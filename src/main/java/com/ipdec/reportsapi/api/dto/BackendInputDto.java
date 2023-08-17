package com.ipdec.reportsapi.api.dto;

import lombok.Data;

@Data
public class BackendInputDto {

    private String nome;

    private String descricao;

    private String url;

    private String senha;

    private String tokenAcesso;

}
