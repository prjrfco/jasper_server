package com.ipdec.reportsapi.api.controller;

import com.ipdec.reportsapi.api.dto.RelatorioDto;
import com.ipdec.reportsapi.domain.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

    @GetMapping
    public List<RelatorioDto> listar(@RequestParam UUID backendId) {
        return service.listar(backendId);
    }

    @GetMapping("/{relatorioId}")
    public RelatorioDto buscar(@RequestParam UUID backendId,
                               @PathVariable UUID relatorioId) {
        return service.buscar(backendId, relatorioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RelatorioDto adicionar(@RequestParam UUID backendId,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        return service.create(file, backendId);
    }

    @PutMapping("/{relatorioId}")
    public RelatorioDto atualizar(@RequestParam UUID backendId,
                                  @PathVariable UUID relatorioId,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        return service.atualizar(backendId, relatorioId, file);
    }

    @DeleteMapping("/{relatorioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@RequestParam UUID backendId,
                        @PathVariable UUID id) {
        service.deletar(backendId, id);
    }
}
