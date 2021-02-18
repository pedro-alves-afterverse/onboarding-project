CREATE TABLE ITEMS_PROFILE
(
    profile_id      VARCHAR(40) NOT NULL UNIQUE,
    item_id        VARCHAR(40) NOT NULL UNIQUE,

    PRIMARY KEY (profile_id, item_id),
    CONSTRAINT fk_profile
        FOREIGN KEY(profile_id)
            REFERENCES profiles(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_item
        FOREIGN KEY(item_id)
            REFERENCES items(id)
            ON DELETE CASCADE
)