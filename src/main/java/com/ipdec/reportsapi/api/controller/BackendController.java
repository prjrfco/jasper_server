package com.ipdec.reportsapi.api.controller;

import com.ipdec.reportsapi.api.dto.BackendDto;
import com.ipdec.reportsapi.api.dto.BackendInputDto;
import com.ipdec.reportsapi.domain.service.BackendService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend")
public class BackendController {

    @Autowired
    private BackendService service;

    @GetMapping
    public List<BackendDto> listar() {
        List<BackendDto> listaDto = service.listar();
        return ResponseEntity.ok(listaDto).getBody();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BackendDto> buscar(@PathVariable Long id) {
        BackendDto dto = service.buscar(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BackendDto adicionar(@Valid @RequestBody BackendInputDto dto) {
        return service.adicionar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BackendDto> atualizar(@PathVariable Long id, @Valid @RequestBody BackendInputDto dto) {
        BackendDto result = service.atualizar(dto, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.deletar(id);
    }
}
