package com.ipdec.reportsapi.config.feignService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ipdec.reportsapi.domain.model.Relatorio;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendRelatorioDto {

    private UUID uuid;

    private String tipo;

    private String nome;

    private Integer versao;

    private Date criadoEm;

    private Date atualizadoEm;

    public SendRelatorioDto() {
    }

    public SendRelatorioDto(Relatorio relatorio) {
        this.uuid = relatorio.getId();
        this.tipo = relatorio.getTipo();
        this.nome = relatorio.getNome();
        this.versao = relatorio.getVersao();
        this.criadoEm = relatorio.getCriadoEm();
        this.atualizadoEm = relatorio.getAtualizadoEm();
    }
}
