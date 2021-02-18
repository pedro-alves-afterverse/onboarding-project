CREATE TYPE regions as ENUM ('SA', 'CA', 'NA', 'AS', 'AF', 'EU', 'OC');

CREATE TABLE profiles
(
    id          VARCHAR(40) NOT NULL UNIQUE,
    username    VARCHAR(64) NOT NULL UNIQUE,
    coin        INTEGER NOT NULL,
    gem         INTEGER NOT NULL,
    money_spent REAL NOT NULL,
    region      regions,

    PRIMARY KEY (id)
);

CREATE INDEX ON profiles (region);