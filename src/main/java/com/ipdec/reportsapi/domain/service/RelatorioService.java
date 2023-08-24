package com.ipdec.reportsapi.domain.service;

import com.ipdec.reportsapi.api.dto.RelatorioDto;
import com.ipdec.reportsapi.api.exceptionhandler.exception.EntidadeNaoEncontradaException;
import com.ipdec.reportsapi.api.exceptionhandler.exception.NegocioException;
import com.ipdec.reportsapi.domain.model.Backend;
import com.ipdec.reportsapi.domain.model.Historico;
import com.ipdec.reportsapi.domain.model.Relatorio;
import com.ipdec.reportsapi.domain.repository.BackendRepository;
import com.ipdec.reportsapi.domain.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Import(FeignClientsConfiguration.class)
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

    @Transactional
    public RelatorioDto create(MultipartFile file, UUID backendId) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Backend backend = backendRepository.findById(backendId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));

        if (repository.findByNomeAndBackendId(fileName, backendId).isPresent()) {
            throw new NegocioException("Nome do relatório já existente para este backend");
        }

        Relatorio relatorio = new Relatorio();
        relatorio.setNome(fileName);
        relatorio.setArquivo(file.getBytes());
        relatorio.setTipo(file.getContentType());
        relatorio.setBackend(backend);
        relatorio.setVersao(1);

        relatorio = repository.save(relatorio);

        return new RelatorioDto(relatorio);
    }

    public RelatorioDto atualizar(UUID backendId, UUID relatorioId, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Relatorio relatorio = repository.findByIdAndAndBackend_Id(relatorioId, backendId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado"));

        Optional<Relatorio> relatorioExistente = repository.findByNomeAndBackendId(fileName, backendId);
        if (relatorioExistente.isPresent() && !relatorioExistente.get().getId().equals(relatorio.getId())) {
            throw new NegocioException("Nome em uso por outro relatório neste backend");
        }

        relatorio.setAtualizadoEm(new Date());
        Historico historico = new Historico(relatorio);
        relatorio.getHistorico().add(historico);

        relatorio.setNome(fileName);
        relatorio.setArquivo(file.getBytes());
        relatorio.setTipo(file.getContentType());
        relatorio.setVersao(relatorio.getVersao() + 1);

        relatorio = repository.save(relatorio);

        return new RelatorioDto(relatorio);
    }

    public void deletar(UUID backendId, UUID id) {
        Relatorio relatorio = repository.findByIdAndAndBackend_Id(id, backendId
        ).orElseThrow(() -> new EntidadeNaoEncontradaException("Relatório não encontrado"));

        try {
            repository.delete(relatorio);
        } catch (Exception e) {
            throw new NegocioException("Não foi possível deletar o relatório: " + e.getMessage());
        }
    }
}
