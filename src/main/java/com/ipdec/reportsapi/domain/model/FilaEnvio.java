package com.ipdec.reportsapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "fila_envio", schema = "gerenciamento")
@Data
public class FilaEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "relatorio_id")
    private Relatorio relatorio;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "criado_em")
    private Date criadoEm = new Date();

    public FilaEnvio() {
    }

    public FilaEnvio(Relatorio relatorio) {
        this.relatorio = relatorio;
        this.criadoEm = new Date();
    }
}
