package com.ipdec.reportsapi.api.controller;

import com.ipdec.reportsapi.api.dto.RelatorioInputDto;
import com.ipdec.reportsapi.domain.service.JasperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/jasper")
public class JasperController {

    @Autowired
    private JasperService service;

    @RequestMapping(method = RequestMethod.POST, value = "/{relatorioId}")
    public String exportarPdf(@RequestHeader String user,
                              @PathVariable UUID relatorioId,
                              @RequestBody RelatorioInputDto dto) throws IOException {

        byte[] bytes = service.exportarPDF(user, relatorioId, dto);
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes);
    }
}
