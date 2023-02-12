CREATE TABLE users
(
    id uuid NOT NULL PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    create_at timestamp NOT NULL
);
