CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE class (
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    teacher_id UUID,
    FOREIGN KEY (teacher_id) REFERENCES users(id)
);

CREATE TABLE teacher_class (
    teacher_id UUID NOT NULL,
    class_id UUID NOT NULL,
    PRIMARY KEY (teacher_id, class_id),
    FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE RESTRICT,
    FOREIGN KEY (class_id) REFERENCES class(id) ON DELETE CASCADE
);
