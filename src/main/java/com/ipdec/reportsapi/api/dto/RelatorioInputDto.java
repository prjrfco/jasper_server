package com.ipdec.reportsapi.api.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RelatorioInputDto {

    private Long id;

    private Map<String, Object> params;

    private List<Object> parameter_list;

}
