CREATE TABLE history_vote
(
    id uuid NOT NULL PRIMARY KEY,
    quote_id uuid NOT NULL  REFERENCES quotes(id),
    user_id uuid NOT NULL  REFERENCES users(id),
    voted_at timestamp NOT NULL,
    vote integer
);
