INSERT INTO student (name)
VALUES
  ('Nikita'),
  ('Maksim'),
  ('Sergey'),
  ('Vova');

INSERT INTO sports_field (name)
VALUES
  ('diving'),
  ('rowing'),
  ('aikido'),
  ('archery'),
  ('boxing'),
  ('karate'),
  ('programming');

INSERT INTO competition_type (name)
VALUES
  ('strength'),
  ('triathlon'),
  ('iron man');

INSERT INTO competition (name, date, competition_type_id, finished)
VALUES
  ('Who is the strongest?', '2019-12-17 00:00:00', 1, false),
  ('Back to water', '2018-12-18 00:00:00', 2, false),
  ('Last years competition', '2018-10-01 00:00:00', 2, true);

INSERT INTO student_field_score (student_id, field_id, score)
VALUES
  (1, 1, 40),
  (1, 2, 50),
  (1, 3, 60),
  (1, 4, 70),
  (1, 5, 70),
  (1, 6, 90),
  (1, 7, 100),

  (2, 1, 20),
  (2, 2, 53),
  (2, 3, 60),
  (2, 4, 30),
  (2, 5, 70),
  (2, 6, 18),
  (2, 7, 10),

  (3, 1, 53),
  (3, 2, 12),
  (3, 3, 16),
  (3, 4, 43),
  (3, 5, 53),
  (3, 6, 93),
  (3, 7, 25),

  (4, 1, 41),
  (4, 2, 12),
  (4, 3, 41),
  (4, 4, 43),
  (4, 5, 54),
  (4, 6, 2),
  (4, 7, 12);

INSERT INTO competition_field_map (competition_id, field_id)
VALUES
  (1, 2),
  (1, 6),
  (1, 5),
  (2, 1),
  (2, 2),
  (3, 2),
  (3, 1);

INSERT INTO competition_student_map (competition_id, student_id)
VALUES
  (3, 1),
  (3, 2),
  (3, 3),
  (2, 1),
  (2, 3);

INSERT INTO competition_results (competition_id, student_id, total_score)
VALUES
  (3, 1, 200),
  (3, 2, 100),
  (3, 3, 50);

INSERT INTO random_enhancement (student_id, min_coefficient, max_coefficient)
VALUES
  (1, 1.00, 2.00);

INSERT INTO previous_competition_enhancement (student_id, coefficient)
VALUES
  (1, 1.5);

INSERT INTO hated_students_enhancement (student_id, hated_student_id, coefficient)
VALUES
  (1, 3, 1.5);



