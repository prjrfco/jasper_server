package com.ipdec.reportsapi.api.dto;

import com.ipdec.reportsapi.domain.model.Backend;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class BackendDto {

    private UUID id;

    private String nome;

    private String descricao;

    private String url;

    private String token;

    private Date createdAt;

    private Date updatedAt;

    public BackendDto() {
    }

    public BackendDto(Backend backend) {
        this.id = backend.getId();
        this.nome = backend.getNome();
        this.descricao = backend.getDescricao();
        this.url = backend.getUrl();
        this.token = backend.getToken();
        this.createdAt = backend.getCreatedAt();
        this.updatedAt = backend.getUpdatedAt();
    }
}
