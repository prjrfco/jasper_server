package com.ipdec.reportsapi.api.controller;

import com.ipdec.reportsapi.api.dto.RelatorioInputDto;
import com.ipdec.reportsapi.domain.service.JasperService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/jasper")
public class JasperController {

    @Autowired
    private JasperService service;

    @RequestMapping(method = RequestMethod.POST, value = "/{relatorioId}")
    public void exportarPdf(@RequestHeader String user,
                            @PathVariable UUID relatorioId,
                            @RequestBody RelatorioInputDto dto,
                            HttpServletResponse response) throws IOException {

        byte[] bytes = service.exportarPDF(user, relatorioId, dto);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "inline; filename=Recibo-" + "teste" + ".pdf");
        response.getOutputStream().write(bytes);
    }
}
