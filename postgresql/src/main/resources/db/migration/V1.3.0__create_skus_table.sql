CREATE TABLE skus
(
    id          VARCHAR(40) NOT NULL UNIQUE,
    coin        INTEGER,
    gem         INTEGER,
    price       REAL,

    PRIMARY KEY (id)
);