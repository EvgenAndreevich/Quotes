CREATE TABLE quotes
(
    id uuid NOT NULL PRIMARY KEY,
    user_id uuid NOT NULL REFERENCES users(id),
    quote varchar(1000) NOT NULL,
    votes integer,
    create_at timestamp NOT NULL,
    update_at timestamp
);
