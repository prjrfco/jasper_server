package com.ipdec.reportsapi.api.controller;

import com.ipdec.reportsapi.domain.model.Backend;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend")
public class BackendController {


    @GetMapping
    public List<Backend> listar() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Backend> buscar(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Backend adicionar(@Valid @RequestBody Backend input) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Backend> atualizar(@Valid @PathVariable Long id, @Valid @RequestBody Backend input) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return null;
    }
}
