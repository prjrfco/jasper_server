--liquibase formatted sql

--changeset bob:1

create table cadastro.relatorio
(
    id            UUID                           DEFAULT uuid_generate_v4(),
    backend_id    UUID REFERENCES cadastro.backend (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    arquivo       BYTEA        NOT NULL,
    tipo          varchar(50)  not null,
    nome          varchar(100) not null,
    versao        integer default 1,
    criado_em     TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP(0) WITHOUT TIME ZONE,
    UNIQUE (backend_id, nome),
    primary key (id)
);