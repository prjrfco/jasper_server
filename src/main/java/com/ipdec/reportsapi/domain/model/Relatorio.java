package com.ipdec.reportsapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "relatorio", schema = "cadastro")
@Data
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "arquivo")
    private byte[] arquivo;

    @Column(name = "tipo")
    private String descricao;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "relatorio")
    private List<Historico> historicos = new ArrayList<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
}
