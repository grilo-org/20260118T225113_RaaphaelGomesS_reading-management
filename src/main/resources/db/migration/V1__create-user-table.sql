CREATE TABLE IF NOT EXISTS TB_USER(
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(155) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(2550) NOT NULL,
    role varchar(10) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

    CONSTRAINT chk_TB_USER_role CHECK (role in ('ADMIN', 'DEFAULT'))
);