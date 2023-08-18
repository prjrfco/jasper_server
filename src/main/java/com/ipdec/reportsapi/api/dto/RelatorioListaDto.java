package com.ipdec.reportsapi.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ipdec.reportsapi.domain.model.Relatorio;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelatorioListaDto {

    private UUID id;

    private String nome;

    public RelatorioListaDto() {
    }

    public RelatorioListaDto(Relatorio relatorio) {
        this.id = relatorio.getId();
        this.nome = relatorio.getNome();
    }
}
