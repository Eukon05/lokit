CREATE TABLE LOKIT_DEVICE_COMMAND
(
    id         UUID PRIMARY KEY NOT NULL,
    command    VARCHAR(32)      NOT NULL,
    status     VARCHAR(32)      NOT NULL,
    device_id  UUID             NOT NULL,
    issued_at  TIMESTAMP        NOT NULL,
    expires_at TIMESTAMP        NOT NULL,
    CONSTRAINT fk_device_command_device_id FOREIGN KEY (device_id) REFERENCES LOKIT_DEVICE (id)
);
