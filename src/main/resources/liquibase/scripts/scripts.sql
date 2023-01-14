-- liquibase formatted sql

-- changeset maxvagan:1
create table login_req
(
    id BIGSERIAL NOT NULL,
    username varchar(255),
    password varchar(255),
    CONSTRAINT login_id_pkey PRIMARY KEY (id)
);
create table register_req
(
    id BIGSERIAL NOT NULL,
    user_id bigint NOT NULL,
    username varchar(255),
    password varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    phone varchar(255),
    role varchar(255),
    CONSTRAINT register_id_pkey PRIMARY KEY (id)
);