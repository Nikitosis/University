ALTER TABLE users
    ADD COLUMN password varchar(256)    not null,
    ADD COLUMN username varchar(128)    not null;