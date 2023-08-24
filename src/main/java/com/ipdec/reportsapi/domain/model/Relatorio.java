package com.ipdec.reportsapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "relatorio", schema = "cadastro")
@Data
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "backend_id")
    private Backend backend;

    @Column(name = "arquivo")
    private byte[] arquivo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "versao")
    private Integer versao;

    @OneToMany(mappedBy = "relatorio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Historico> historico = new ArrayList<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "criado_em")
    private Date criadoEm = new Date();

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "atualizado_em")
    private Date atualizadoEm;

    public Relatorio() {
    }
}
