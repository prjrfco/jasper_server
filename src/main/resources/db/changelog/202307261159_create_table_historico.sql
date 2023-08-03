--liquibase formatted sql

--changeset bob:1

create table cadastro.historico
(
    id           UUID                           DEFAULT uuid_generate_v4(),
    relatorio_id UUID REFERENCES cadastro.relatorio (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    arquivo      BYTEA        NOT NULL,
    tipo         varchar(50)  not null,
    nome         varchar(100) not null,
    versao       integer,
    criado_em    TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    primary key (id)
);