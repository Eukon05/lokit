CREATE TABLE LOKIT_ROLE
(
    id          UUID PRIMARY KEY NOT NULL,
    name        VARCHAR(100)     NOT NULL,
    description VARCHAR(500)     NOT NULL,
    active      BOOLEAN          NOT NULL,
    created_at  TIMESTAMP        NOT NULL,
    updated_at  TIMESTAMP        NOT NULL
);

CREATE TABLE LOKIT_USER
(
    id         UUID PRIMARY KEY NOT NULL,
    created_at TIMESTAMP        NOT NULL,
    updated_at TIMESTAMP        NOT NULL
);

CREATE TABLE LOKIT_USER_ROLE
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES LOKIT_USER (id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES LOKIT_ROLE (id)
);