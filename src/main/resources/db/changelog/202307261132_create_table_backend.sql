--liquibase formatted sql

--changeset bob:1

CREATE SCHEMA IF NOT EXISTS cadastro;

create table cadastro.backend
(
    id bigserial,
    nome varchar(100) not null,
    descricao text not null,
    url varchar(100) not null,
    token varchar(20) not null,
    created_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(0) WITHOUT TIME ZONE,
    primary key (id)
);

insert into cadastro.backend values (default, 'Balandrau', 'API de gerenciamento marçônico balandrau', 'http://localhost:8081', '123', default, null);