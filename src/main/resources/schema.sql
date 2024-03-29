USE chess;

CREATE TABLE pieces
(
    piece_type VARCHAR(10) NOT NULL,
    color      VARCHAR(5)  NOT NULL,
    x          VARCHAR(1)  NOT NULL,
    y          VARCHAR(5)  NOT NULL,
    PRIMARY KEY (x, y)
);
