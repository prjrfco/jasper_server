--liquibase formatted sql

--changeset bob:1

CREATE SCHEMA IF NOT EXISTS cadastro;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table cadastro.backend
(
    id            UUID                           DEFAULT uuid_generate_v4(),
    nome          varchar(100) not null unique,
    descricao     text         not null,
    url           varchar(100) not null,
    senha         varchar(100) not null,
    token_acesso  varchar(100),
    criado_em     TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP(0) WITHOUT TIME ZONE,
    primary key (id)
);