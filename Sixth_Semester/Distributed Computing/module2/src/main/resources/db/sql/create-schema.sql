CREATE TABLE teacher(
    id      serial   primary key,
    name    varchar(128)    not null
);

CREATE TABLE subject(
    id      serial          primary key,
    name    varchar(128)    not null
);

CREATE TABLE lesson(
    id              serial          primary key,
    teacher_id      int4            not null    REFERENCES teacher(id),
    subject_id      int4            not null    REFERENCES subject(id),
    day_in_week     varchar(128)    not null,
    audience        int4            not null,
    students_amount int4            not null
);