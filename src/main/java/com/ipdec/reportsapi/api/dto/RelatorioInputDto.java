package com.ipdec.reportsapi.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class RelatorioInputDto {

    private String nome;

    private Map<String, Object> params;

    private List<Object> parameterList = new ArrayList<>();

}
