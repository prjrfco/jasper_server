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
    criado_em     TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP(0) WITHOUT TIME ZONE,
    primary key (id)
);

insert into cadastro.backend
values (default, 'balandrau', 'API de gerenciamento marçônico balandrau', 'http://localhost:8081/relatorios',
        '$2a$12$d7BerEyoctDTdugqhareC.ifjwUU3smFZWxKWHuZGLQICrDnerGdK', default,
        null);