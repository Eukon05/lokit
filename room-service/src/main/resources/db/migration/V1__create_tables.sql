CREATE TABLE LOKIT_ROOM
(
    id          UUID PRIMARY KEY NOT NULL,
    name        VARCHAR(100)     NOT NULL,
    description VARCHAR(500)     NOT NULL,
    active      BOOLEAN          NOT NULL
);

CREATE TABLE LOKIT_ROOM_ROLES
(
    room_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (room_id, role_id),
    CONSTRAINT fk_room_id FOREIGN KEY (room_id) REFERENCES LOKIT_ROOM (id)
);