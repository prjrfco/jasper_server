package com.ipdec.reportsapi.domain.service;

import com.ipdec.reportsapi.api.dto.BackendDto;
import com.ipdec.reportsapi.api.dto.BackendInputDto;
import com.ipdec.reportsapi.api.exceptionhandler.exception.EntidadeNaoEncontradaException;
import com.ipdec.reportsapi.domain.model.Backend;
import com.ipdec.reportsapi.domain.repository.BackendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BackendService {

    @Autowired
    private BackendRepository repository;

    public List<BackendDto> listar() {
        List<Backend> lista = repository.findAll();

        return lista.stream().map(BackendDto::new).toList();
    }

    public BackendDto buscar(UUID id) {
        Backend backend = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));

        return new BackendDto(backend);
    }

    @Transactional
    public BackendDto adicionar(BackendInputDto dto) {
        Backend backend = new Backend(dto);

        backend = repository.save(backend);
        return new BackendDto(backend);
    }

    @Transactional
    public BackendDto atualizar(BackendInputDto dto, UUID id) {
        Backend backend = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));

        backend.setNome(dto.getNome());
        backend.setDescricao(dto.getDescricao());
        backend.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));
        backend.setAtualizadoEm(new Date());

        backend = repository.save(backend);
        return new BackendDto(backend);
    }

    @Transactional
    public void deletar(UUID id) {
        Backend backend = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Backend não encontrado"));

        repository.delete(backend);
    }
}
