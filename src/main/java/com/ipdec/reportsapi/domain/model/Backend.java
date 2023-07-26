package com.ipdec.reportsapi.domain.model;

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
}
