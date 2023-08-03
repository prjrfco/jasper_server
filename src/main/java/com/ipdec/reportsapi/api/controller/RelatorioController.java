package com.ipdec.reportsapi.api.controller;

import com.ipdec.reportsapi.api.dto.RelatorioDto;
import com.ipdec.reportsapi.api.dto.RelatorioInputDto;
import com.ipdec.reportsapi.domain.model.Relatorio;
import com.ipdec.reportsapi.domain.service.RelatorioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/backend/{backendId}/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

    @GetMapping
    public List<RelatorioDto> listar(@PathVariable UUID backendId) {
        return service.listar(backendId);
    }

    @GetMapping("/{relatorioId}")
    public RelatorioDto buscar(@PathVariable UUID backendId,
                               @PathVariable UUID relatorioId) {
        return service.buscar(backendId, relatorioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RelatorioDto adicionar(@PathVariable UUID backendId,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        return service.create(file, backendId);
    }

    @PutMapping("/{relatorioId}")
    public ResponseEntity<Relatorio> atualizar(@PathVariable UUID backendId,
                                               @PathVariable UUID relatorioId,
                                               @RequestParam("file") MultipartFile file) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exportar/{relatorioId}")
    public void exportarPdf(@PathVariable UUID backendId,
                            @PathVariable UUID relatorioId,
                            @RequestBody RelatorioInputDto dto,
                            HttpServletResponse response) throws IOException {

        byte[] bytes = service.exportarPDF(backendId, relatorioId, dto);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "inline; filename=Recibo-" + "teste" + ".pdf");
        response.getOutputStream().write(bytes);
    }
}
