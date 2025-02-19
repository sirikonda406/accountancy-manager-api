create table INDEX_DATABASE
(
    id_code          varchar NOT NULL PRIMARY KEY,
    id_state         varchar(255),
    id_dialect       varchar(255),
    id_driver        varchar(255),
    id_url           varchar(255),
    id_username      varchar(255),
    id_password      varchar(255),
    id_schema        varchar(255),
    id_configuration varchar(255)
);