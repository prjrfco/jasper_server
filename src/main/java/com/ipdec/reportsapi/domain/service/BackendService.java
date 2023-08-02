package com.ipdec.reportsapi.domain.service;

import com.ipdec.reportsapi.api.dto.BackendInputDto;
import com.ipdec.reportsapi.api.exceptionhandler.exception.EntidadeNaoEncontradaException;
import com.ipdec.reportsapi.domain.model.Backend;
import com.ipdec.reportsapi.domain.repository.BackendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BackendService {

    @Autowired
    private BackendRepository repository;

    public List<Backend> listar() {
        List<Backend> lista = repository.findAll();
        return lista;
    }

    public Backend buscar(Long id) {
        Backend backend = repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));
        return backend;
    }

    @Transactional
    public Backend criar(BackendInputDto dto) {
        Backend backend = new Backend(dto);
        backend = repository.save(backend);
        return backend;
    }

    @Transactional
    public Backend atualizar(BackendInputDto dto, Long id) {
        Backend backend = repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));

        backend.setNome(dto.getNome());
        backend.setDescricao(dto.getDescricao());
        backend.setUrl(dto.getUrl());
        backend.setToken(dto.getToken());

        backend = repository.save(backend);
        return backend;
    }

    @Transactional
    public void deletar(BackendInputDto dto, Long id) {
        Backend backend = repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));

        repository.delete(backend);
    }
}
