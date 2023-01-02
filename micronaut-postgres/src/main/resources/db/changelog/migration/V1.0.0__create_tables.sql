CREATE TABLE universities
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR NOT NULL
);

CREATE TABLE grades
(
    id              INT PRIMARY KEY,
    grade           VARCHAR NOT NULL,
    description     TEXT
);

CREATE TABLE students
(
    id              SERIAL PRIMARY KEY,
    first_name      VARCHAR,
    last_name       VARCHAR,
    university_id   INT NOT NULL REFERENCES universities(id),
    grade_id        INT NOT NULL REFERENCES grades(id)
);

CREATE TABLE scores
(
    student_id      INT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
    test1_score     NUMERIC NOT NULL,
    test2_score     NUMERIC NOT NULL,
    test3_score     NUMERIC NOT NULL,
    test4_score     NUMERIC NOT NULL,
    final_score     NUMERIC NOT NULL
);