CREATE TABLE student(
  id      BIGSERIAL     PRIMARY KEY,
  name    TEXT          NOT NULL
);

CREATE TABLE sports_field(
  id      BIGSERIAL     PRIMARY KEY,
  name    TEXT          NOT NULL
);

CREATE TABLE competition_type(
  id      BIGSERIAL     PRIMARY KEY,
  name    TEXT          NOT NULL
);

CREATE TABLE competition(
  id                    BIGSERIAL     PRIMARY KEY,
  name                  TEXT          NOT NULL,
  date                  TIMESTAMP     NOT NULL,
  competition_type_id   BIGSERIAL     NOT NULL,
  finished              BOOLEAN       NOT NULL DEFAULT FALSE,
  CONSTRAINT competition_competition_type_fk  FOREIGN KEY (competition_type_id)
  REFERENCES competition_type(id)
);

CREATE TABLE student_field_score(
  id          BIGSERIAL     PRIMARY KEY,
  student_id  BIGSERIAL     NOT NULL,
  field_id    BIGSERIAL     NOT NULL,
  score       INT           NOT NULL,
  CONSTRAINT  student_field_score_student_fk  FOREIGN KEY (student_id) REFERENCES student(id),
  CONSTRAINT  student_field_score_field_fk    FOREIGN KEY (field_id) REFERENCES sports_field(id),
  CONSTRAINT  student_field_score_score_valid CHECK(score >= 0 AND score <= 100),
  CONSTRAINT  student_field_score_uk          UNIQUE(student_id, field_id)
);

CREATE TABLE competition_field_map(
  id              BIGSERIAL     PRIMARY KEY,
  competition_id  BIGSERIAL     NOT NULL,
  field_id        BIGSERIAL     NOT NULL,
  CONSTRAINT competition_field_map_competition_fk FOREIGN KEY (competition_id)
  REFERENCES competition(id),
  CONSTRAINT competition_field_map_field_fk FOREIGN KEY (field_id)
  REFERENCES sports_field(id),
  CONSTRAINT  competition_field_map_uk UNIQUE(competition_id, field_id)
);

CREATE TABLE competition_student_map(
  id              BIGSERIAL     PRIMARY KEY,
  competition_id  BIGSERIAL     NOT NULL,
  student_id      BIGSERIAL     NOT NULL,
  CONSTRAINT competition_student_map_student_fk FOREIGN KEY (student_id)
  REFERENCES student(id),
  CONSTRAINT competition_student_map_competition_fk FOREIGN KEY (competition_id)
  REFERENCES competition(id),
  CONSTRAINT  competition_student_map_uk  UNIQUE(competition_id, student_id)
);

CREATE TABLE competition_results(
  id              BIGSERIAL     PRIMARY KEY,
  competition_id  BIGSERIAL     NOT NULL,
  student_id      BIGSERIAL     NOT NULL,
  total_score     INT           NOT NULL,
  CONSTRAINT competition_results_student_fk FOREIGN KEY (student_id)
  REFERENCES student(id),
  CONSTRAINT competition_results_competition_fk FOREIGN KEY (competition_id)
  REFERENCES competition(id),
  CONSTRAINT competition_results_uk UNIQUE(competition_id, student_id)
);

CREATE TABLE hated_students_enhancement(
  id                BIGSERIAL         PRIMARY KEY,
  student_id        BIGSERIAL         NOT NULL,
  hated_student_id  BIGSERIAL         NOT NULL,
  coefficient       NUMERIC(9, 2)     NOT NULL,
  CONSTRAINT hated_students_uk    UNIQUE (student_id, hated_student_id),
  CONSTRAINT hated_students_student_fk FOREIGN KEY (student_id)
  REFERENCES student(id),
  CONSTRAINT hated_students_hated_student_fk FOREIGN KEY (student_id)
  REFERENCES student(id),
  CONSTRAINT hated_students_valid CHECK(student_id != hated_student_id)
);

CREATE TABLE random_enhancement(
  id                BIGSERIAL      PRIMARY KEY,
  student_id        BIGSERIAL      NOT NULL,
  min_coefficient   NUMERIC(9, 2)  NOT NULL,
  max_coefficient   NUMERIC(9, 2)  NOT NULL,
  CONSTRAINT random_enhancement_fk FOREIGN KEY (student_id)
  REFERENCES student(id)
);

CREATE TABLE previous_competition_enhancement(
  id                BIGSERIAL       PRIMARY KEY,
  student_id        BIGSERIAL       NOT NULL,
  coefficient       NUMERIC(9, 2)   NOT NULL,
  CONSTRAINT previous_competition_enhancement_fk FOREIGN KEY (student_id)
  REFERENCES student(id)
)
