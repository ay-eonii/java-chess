USE chess;

CREATE TABLE squares
(
    id         INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    piece_type VARCHAR(10) NOT NULL,
    color      VARCHAR(5)  NOT NULL,
    x          VARCHAR(1)  NOT NULL,
    y          VARCHAR(5)  NOT NULL
);

CREATE TABLE turn
(
    id    INT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    color VARCHAR(5) NOT NULL
);
