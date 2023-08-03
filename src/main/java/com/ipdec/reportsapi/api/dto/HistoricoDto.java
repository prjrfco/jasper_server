package com.ipdec.reportsapi.api.dto;

import com.ipdec.reportsapi.domain.model.Historico;
import lombok.Data;

import java.util.Date;

@Data
public class HistoricoDto {

    private Integer versao;

    private Date criadoEm;

    public HistoricoDto() {
    }

    public HistoricoDto(Historico historico) {
        this.versao = historico.getVersao();
        this.criadoEm = historico.getCriadoEm();
    }
}
