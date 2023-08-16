--liquibase formatted sql

--changeset bob:1

CREATE SCHEMA IF NOT EXISTS gerenciamento;

create table gerenciamento.fila_envio
(
    id           UUID DEFAULT uuid_generate_v4(),
    relatorio_id UUID REFERENCES cadastro.relatorio (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    criado_em    TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    primary key (id)
);