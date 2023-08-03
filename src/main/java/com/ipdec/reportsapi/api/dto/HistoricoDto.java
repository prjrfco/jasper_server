package com.ipdec.reportsapi.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ipdec.reportsapi.domain.model.Historico;
import lombok.Data;

import java.util.Date;

@Data
public class HistoricoDto {

    private Integer versao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date criadoEm;

    public HistoricoDto() {
    }

    public HistoricoDto(Historico historico) {
        this.versao = historico.getVersao();
        this.criadoEm = historico.getCriadoEm();
    }
}
