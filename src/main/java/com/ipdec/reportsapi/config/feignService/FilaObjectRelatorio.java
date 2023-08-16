package com.ipdec.reportsapi.config.feignService;

import lombok.Data;

import java.net.URI;

@Data
public class FilaObjectRelatorio {

    private SendRelatorioDto dto;
    private URI uri;

    public FilaObjectRelatorio() {
    }
}
