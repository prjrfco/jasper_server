--liquibase formatted sql

--changeset bob:1

create table cadastro.historico
(
    id bigserial,
    relatorio_id bigint REFERENCES cadastro.relatorio (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    tipo varchar(50) not null,
    nome varchar(100) not null,
    created_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    primary key (id)
);