USE chess;

CREATE TABLE squares
(
    piece_type VARCHAR(10) NOT NULL,
    color      VARCHAR(5)  NOT NULL,
    x          VARCHAR(1)  NOT NULL,
    y          VARCHAR(5)  NOT NULL,
    PRIMARY KEY (x, y)
);

CREATE TABLE turn
(
    color VARCHAR(5) NOT NULL
);
