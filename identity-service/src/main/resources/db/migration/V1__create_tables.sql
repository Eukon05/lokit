CREATE TABLE LOKIT_IDP_USER
(
    id         UUID PRIMARY KEY NOT NULL,
    idp_id     VARCHAR(255)     NOT NULL UNIQUE,
    first_name VARCHAR(100)     NOT NULL,
    last_name  VARCHAR(100)     NOT NULL,
    email      VARCHAR(320)     NOT NULL
);