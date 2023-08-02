package com.ipdec.reportsapi.domain.model;

import com.ipdec.reportsapi.api.dto.BackendInputDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "backend", schema = "cadastro")
@Data
public class Backend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "url")
    private String url;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public Backend() {
    }

    public Backend(BackendInputDto dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.url = dto.getUrl();
        this.token = new BCryptPasswordEncoder().encode(dto.getToken());
        this.createdAt = new Date();
    }
}
