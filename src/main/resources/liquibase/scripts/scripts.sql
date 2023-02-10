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

-- changeset maxvagan:2
create table users
(
    user_id bigint NOT NULL,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    phone varchar(255),
    role varchar(255),
    avatar_path varchar(255),
    reg_date timestamp without time zone,
    CONSTRAINT user_id_pkey PRIMARY KEY (user_id)
);

alter table register_req add column email varchar(255);
alter table register_req
    add constraint user_id_fkey foreign key(user_id)
        REFERENCES users(user_id);

create table ads
(
    id BIGSERIAL NOT NULL,
    user_id bigint REFERENCES users(user_id),
    label varchar(255),
    description text,
    price REAL,
    currency varchar(3),
    status varchar(255),
    image_list_id bigint,
    CONSTRAINT ads_id_pkey PRIMARY KEY (id)
);

create table ads_images
(
    id BIGSERIAL NOT NULL,
    image_id bigint NOT NULL,
    image_path varchar(255),
    CONSTRAINT ads_images_id_pkey PRIMARY KEY (id)
);

alter table ads
    add constraint image_list_id_fkey foreign key(image_list_id)
        REFERENCES ads_images(id);

-- changeset maxvagan:3
create table comment
(
    id BIGSERIAL NOT NULL,
    user_id bigint REFERENCES users(user_id),
    ads_id bigint REFERENCES ads(id),
    comment text,
    create_date timestamp without time zone,
    CONSTRAINT comment_id_pkey PRIMARY KEY (id)
);

--changeset mkachalov:1
alter table ads
    add title text;

-- changeset maxvagan:4
alter table users add column username varchar(255);
alter table users add column password varchar(255);
drop table register_req;
drop table login_req;

--changeset mkachalov:2
alter table ads
alter column id type bigint using id::bigint;

--changeset mkachalov:3
alter table comment
alter column ads_id type bigint using ads_id::bigint;

--changeset mkachalov:4
alter table ads
alter column price type integer using price::integer;

--changeset mkachalov:5
alter table ads
alter column price type REAL using price::REAL;

-- changeset vasilydemin:1
alter table ads_images
    rename column image_id to ids_id;

-- changeset vasilydemin:2
alter table ads_images
    rename column ids_id to ads_id;

-- changeset vasilydemin:3
alter table ads drop column image_list_id;

-- changeset maxvagan:5
alter table ads drop column currency;
alter table ads drop column status;
alter table ads drop column label;

-- changeset maxvagan:6
alter table users add column enabled smallint;

-- changeset maxvagan:7
create table authorities
(
    id BIGSERIAL NOT NULL,
    username varchar(255),
    authority varchar(255),
    CONSTRAINT authority_id_pkey PRIMARY KEY (id)
);

-- changeset maxvagan:8
alter table users drop column enabled;
alter table users add column enabled boolean;

-- changeset maxvagan:9
CREATE SEQUENCE "public"."users_id_seq";
ALTER TABLE users ALTER COLUMN user_id SET DEFAULT nextval('"public"."users_id_seq"'::regclass);
