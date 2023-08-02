package com.ipdec.reportsapi.domain.model;

import com.ipdec.reportsapi.api.dto.BackendInputDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;


@Entity
@Table(name = "backend", schema = "cadastro")
@Data
public class Backend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "url")
    private String url;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public Backend() {
    }

    public Backend(BackendInputDto dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.url = dto.getUrl();
        this.token = dto.getToken();
    }
}
