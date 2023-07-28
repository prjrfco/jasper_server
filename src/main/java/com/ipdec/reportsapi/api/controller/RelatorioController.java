package com.ipdec.reportsapi.api.controller;

import com.ipdec.reportsapi.domain.model.Relatorio;
import com.ipdec.reportsapi.domain.service.RelatorioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService service;

    @GetMapping
    public List<Relatorio> listar() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscar(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Relatorio adicionar(@RequestParam("file") MultipartFile file) throws IOException {
        Relatorio relatorio = service.create(file);
        return relatorio;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Relatorio> atualizar(@Valid @PathVariable Long id, @Valid @RequestBody Relatorio input) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/exportar/{id}")
    public void exportarPdf(@PathVariable Long id,
                            HttpServletResponse response) throws IOException {

        service.addParams("P_DESCRICAO", "TESTE Ç;:<>!@#$%¨&*()!");
        byte[] bytes = service.exportarPDF(id);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "inline; filename=Recibo-" + "teste" + ".pdf");
        response.getOutputStream().write(bytes);
    }
}
