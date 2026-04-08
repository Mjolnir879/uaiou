ALTER TABLE users
    ADD COLUMN password_hash VARCHAR(255);

UPDATE users
SET password_hash = '$2a$10$8QmN5PX5A5e9gbf28pQqXeENHq8Yp2uR2m67R6V3hbWfYkRk1eKtu'
WHERE password_hash IS NULL;

ALTER TABLE users
    ALTER COLUMN password_hash SET NOT NULL;
