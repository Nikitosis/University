CREATE TABLE users (
    id          bigserial   primary key,
    first_name  varchar(128)    not null,
    last_name   varchar(128)    not null
);

CREATE TABLE bank_account (
    id      bigserial   primary key,
    user_id int8        not null REFERENCES users(id),
    balance numeric     not null,
    status  varchar(32) not null
);

CREATE TABLE credit_card (
    id              bigserial   primary key,
    name            varchar(128)    not null,
    bank_account_id int8    not null
);

CREATE TABLE transaction (
    id              bigserial   primary key,
    from_account    int8        not null REFERENCES bank_account(id),
    to_account      int8        not null REFERENCES bank_account(id),
    amount          numeric     not null,
    created_at      timestamp   not null
);