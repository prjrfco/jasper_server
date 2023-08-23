--liquibase formatted sql

--changeset bob:1

CREATE SCHEMA IF NOT EXISTS acesso;

create table acesso.users
(
    id        TEXT PRIMARY KEY UNIQUE NOT NULL,
    login     TEXT                    NOT NULL UNIQUE,
    password  TEXT                    NOT NULL,
    role      TEXT                    NOT NULL,
    criado_em TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);