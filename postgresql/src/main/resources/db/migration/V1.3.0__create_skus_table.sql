CREATE TABLE skus
(
    id          VARCHAR(32) NOT NULL UNIQUE,
    coin        INTEGER,
    gem         INTEGER,
    price       REAL,

    PRIMARY KEY (id)
);