CREATE TABLE producers  (
id      bigserial       primary key,
name    varchar(128)    not null
);

CREATE TABLE product (
id          bigserial       primary key,
year        int4            not null,
version     int4            not null,
name        varchar(128)    not null,
producer_id int8            not null    REFERENCES producers(id)
);