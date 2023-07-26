package com.ipdec.reportsapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "historico", schema = "cadastro")
@Data
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "relatorio_id")
    private Relatorio relatorio;

    @Column(name = "tipo")
    private String descricao;

    @Column(name = "nome")
    private String nome;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();
}
