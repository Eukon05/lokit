CREATE TABLE LOKIT_USER
(
    id         UUID PRIMARY KEY NOT NULL,
    created_at TIMESTAMP        NOT NULL,
    updated_at TIMESTAMP        NOT NULL
);

CREATE TABLE LOKIT_CARD
(
    id         VARCHAR(8) PRIMARY KEY NOT NULL,
    user_id    UUID                   NOT NULL,
    name       VARCHAR(100)           NOT NULL,
    active     BOOLEAN                NOT NULL,
    created_at TIMESTAMP              NOT NULL,
    updated_at TIMESTAMP              NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES LOKIT_USER (id)
);