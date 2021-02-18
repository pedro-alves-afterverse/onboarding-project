CREATE TABLE items
(
    id          VARCHAR(40) NOT NULL UNIQUE,
    category    VARCHAR(64) NOT NULL,
    image       VARCHAR(512) NOT NULL,
    coin_price  INTEGER,
    gem_price   INTEGER,

    PRIMARY KEY (id)
);