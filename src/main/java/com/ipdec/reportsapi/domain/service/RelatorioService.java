package com.ipdec.reportsapi.domain.service;

import com.ipdec.reportsapi.api.dto.RelatorioDto;
import com.ipdec.reportsapi.api.exceptionhandler.exception.EntidadeNaoEncontradaException;
import com.ipdec.reportsapi.domain.model.Backend;
import com.ipdec.reportsapi.domain.model.Historico;
import com.ipdec.reportsapi.domain.model.Relatorio;
import com.ipdec.reportsapi.domain.repository.BackendRepository;
import com.ipdec.reportsapi.domain.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository repository;

    @Autowired
    private BackendRepository backendRepository;

    public List<RelatorioDto> listar(UUID id) {
        List<Relatorio> lista = repository.findAllByBackendId(id);

        return lista.stream().map(RelatorioDto::new).toList();
    }

    public RelatorioDto buscar(UUID backendId, UUID relatorioId) {
        Relatorio relatorio = repository.findByIdAndAndBackend_Id(relatorioId, backendId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado"));

        return new RelatorioDto(relatorio);
    }

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

        return new RelatorioDto(repository.save(relatorio));
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

        return new RelatorioDto(repository.save(relatorio));
    }
}
