CREATE TABLE upload
(
    id            UUID NOT NULL,
    user_id       UUID NOT NULL,
    creation_date date DEFAULT NOW(),
    CONSTRAINT pk_upload PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id         UUID NOT NULL,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    cpf        VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

insert into "user" (id, name, email, cpf, created_at, updated_at) values ('ad55ba5e-4eb4-4d6f-82da-7f22ec685f0e', 'John Doe', 'john.doe@email.com', '12345678901', now(), now());

CREATE TABLE video
(
    id               UUID    NOT NULL,
    name             TEXT    NOT NULL,
    size             INTEGER NOT NULL,
    content_type     VARCHAR(255) NOT NULL,
    uri              TEXT   NOT NULL,
    upload_id        UUID NOT NULL,
    CONSTRAINT pk_video PRIMARY KEY (id)
);

ALTER TABLE upload
    ADD CONSTRAINT FK_UPLOAD_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE video
    ADD CONSTRAINT FK_VIDEO_ON_UPLOADENTITY FOREIGN KEY (upload_id) REFERENCES upload (id);