CREATE TABLE user_role (
id      serial  primary key,
name    varchar(64) not null unique
);

CREATE TABLE users_user_role (
    user_id         int8    not null    REFERENCES users(id),
    user_role_id    int4    not null    REFERENCES user_role(id),
    PRIMARY KEY (user_id, user_role_id)
);