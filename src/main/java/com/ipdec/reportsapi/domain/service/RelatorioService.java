package com.ipdec.reportsapi.domain.service;

import com.ipdec.reportsapi.api.dto.RelatorioDto;
import com.ipdec.reportsapi.api.exceptionhandler.exception.EntidadeNaoEncontradaException;
import com.ipdec.reportsapi.config.feignService.ApiService;
import com.ipdec.reportsapi.config.feignService.SendRelatorioDto;
import com.ipdec.reportsapi.domain.model.Backend;
import com.ipdec.reportsapi.domain.model.FilaEnvio;
import com.ipdec.reportsapi.domain.model.Historico;
import com.ipdec.reportsapi.domain.model.Relatorio;
import com.ipdec.reportsapi.domain.repository.BackendRepository;
import com.ipdec.reportsapi.domain.repository.FilaEnvioRepository;
import com.ipdec.reportsapi.domain.repository.RelatorioRepository;
import feign.Feign;
import feign.Target;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
@Import(FeignClientsConfiguration.class)
public class RelatorioService {

    @Autowired
    private RelatorioRepository repository;

    @Autowired
    private BackendRepository backendRepository;

    @Autowired
    private FilaEnvioRepository filaEnvioRepository;

    ApiService apiService;

    @Autowired
    public void FeignDemoController(Decoder decoder, Encoder encoder) {
        apiService = Feign.builder().encoder(encoder).decoder(decoder)
                .target(Target.EmptyTarget.create(ApiService.class));
    }

    public List<RelatorioDto> listar(UUID id) {
        List<Relatorio> lista = repository.findAllByBackendId(id);

        return lista.stream().map(RelatorioDto::new).toList();
    }

    public RelatorioDto buscar(UUID backendId, UUID relatorioId) {
        Relatorio relatorio = repository.findByIdAndAndBackend_Id(relatorioId, backendId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado"));

        return new RelatorioDto(relatorio);
    }

    @Transactional
    public RelatorioDto create(MultipartFile file, UUID backendId) throws IOException {
        Backend backend = backendRepository.findById(backendId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Relatorio relatorio = new Relatorio();
        relatorio.setNome(fileName);
        relatorio.setArquivo(file.getBytes());
        relatorio.setTipo(file.getContentType());
        relatorio.setBackend(backend);
        relatorio.setVersao(1);

        relatorio = repository.save(relatorio);

        try {
            this.apiService.sendReport(new URI(backend.getUrl()), new SendRelatorioDto(relatorio));
        } catch (Exception e) {
            filaEnvioRepository.save(new FilaEnvio(relatorio));
            e.printStackTrace();
        }

        return new RelatorioDto(relatorio);
    }

    public RelatorioDto atualizar(UUID backendId, UUID relatorioId, MultipartFile file) throws IOException {
        Relatorio relatorio = repository.findByIdAndAndBackend_Id(relatorioId, backendId
        ).orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado"));

        relatorio.setAtualizadoEm(new Date());
        Historico historico = new Historico(relatorio);
        relatorio.getHistorico().add(historico);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        relatorio.setNome(fileName);
        relatorio.setArquivo(file.getBytes());
        relatorio.setTipo(file.getContentType());
        relatorio.setVersao(relatorio.getVersao() + 1);

        relatorio = repository.save(relatorio);

        Optional<FilaEnvio> presente = filaEnvioRepository.findByRelatorioId(relatorio.getId());

        boolean erro = false;
        try {
            this.apiService.sendReport(new URI(relatorio.getBackend().getUrl()), new SendRelatorioDto(relatorio));
        } catch (Exception e) {
            erro = true;
            if (presente.isEmpty()) {
                presente = Optional.of(new FilaEnvio(relatorio));
            } else {
                presente.get().setCriadoEm(new Date());
            }
            filaEnvioRepository.save(presente.get());
            e.printStackTrace();
        }

        if (presente.isPresent() && !erro) {
            filaEnvioRepository.delete(presente.get());
        }

        return new RelatorioDto(relatorio);
    }
}
