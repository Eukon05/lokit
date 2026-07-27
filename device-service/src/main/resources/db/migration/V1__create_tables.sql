CREATE TABLE LOKIT_DEVICE
(
    id               UUID PRIMARY KEY NOT NULL,
    name             VARCHAR(100)     NOT NULL,
    description      VARCHAR(500)     NOT NULL,
    physical_address VARCHAR(17)      NOT NULL,
    room_id          UUID,
    token_hash       VARCHAR(64)
);