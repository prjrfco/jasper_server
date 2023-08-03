package com.ipdec.reportsapi.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ipdec.reportsapi.domain.model.Relatorio;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelatorioDto {

    private UUID id;

    private String tipo;

    private String nome;

    private Integer versao;

    private List<HistoricoDto> historico = new ArrayList<>();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date criadoEm;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date atualizadoEm;

    public RelatorioDto() {
    }

    public RelatorioDto(Relatorio relatorio) {
        this.id = relatorio.getId();
        this.tipo = relatorio.getTipo();
        this.nome = relatorio.getNome();
        this.versao = relatorio.getVersao();
        if (relatorio.getHistorico() != null && relatorio.getHistorico().size() > 0)
            this.historico = relatorio.getHistorico().stream().map(HistoricoDto::new).toList();
        this.criadoEm = relatorio.getCriadoEm();
        this.atualizadoEm = relatorio.getAtualizadoEm();
    }
}
