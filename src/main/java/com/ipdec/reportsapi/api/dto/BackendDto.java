package com.ipdec.reportsapi.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ipdec.reportsapi.domain.model.Backend;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BackendDto {

    private UUID id;

    private String nome;

    private String descricao;

    private String url;

    private String senha;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date criadoEm;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date atualizadoEm;

    public BackendDto() {
    }

    public BackendDto(Backend backend) {
        this.id = backend.getId();
        this.nome = backend.getNome();
        this.descricao = backend.getDescricao();
        this.senha = backend.getSenha();
        this.criadoEm = backend.getCriadoEm();
        this.atualizadoEm = backend.getAtualizadoEm();
    }
}
