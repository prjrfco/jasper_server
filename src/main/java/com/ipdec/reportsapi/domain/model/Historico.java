package com.ipdec.reportsapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "historico", schema = "cadastro")
@Data
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "relatorio_id")
    private Relatorio relatorio;

    @Column(name = "arquivo")
    private byte[] arquivo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "versao")
    private Integer versao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "criado_em")
    private Date criadoEm = new Date();

    public Historico() {
    }

    public Historico(Relatorio relatorio) {
        this.relatorio = relatorio;
        this.arquivo = relatorio.getArquivo();
        this.tipo = relatorio.getTipo();
        this.nome = relatorio.getNome();
        this.versao = relatorio.getVersao();
        this.criadoEm = new Date();
    }
}
