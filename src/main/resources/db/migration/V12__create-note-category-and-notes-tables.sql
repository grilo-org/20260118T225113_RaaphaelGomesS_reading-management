CREATE TABLE IF NOT EXISTS TB_NOTE_CATEGORY(
    note_category_id BIGSERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_note_category_to_user FOREIGN KEY (user_id) REFERENCES TB_USER(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TB_NOTE(
    note_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(100),
    book_id BIGINT,
    note_category_id BIGINT,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_note_user_title UNIQUE (user_id, title),
    CONSTRAINT chk_TB_NOTE_type CHECK (type in ('QUICK', 'REFERENCE', 'PERMANENT')),
    CONSTRAINT fk_note_to_book FOREIGN KEY (book_id) REFERENCES TB_BOOK(book_id) ON DELETE SET NULL,
    CONSTRAINT fk_note_to_note_category FOREIGN KEY (note_category_id) REFERENCES TB_NOTE_CATEGORY(note_category_id) ON DELETE SET NULL,
    CONSTRAINT fk_note_to_user FOREIGN KEY (user_id) REFERENCES TB_USER(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TB_NOTE_LINK(
    source_note_id BIGINT NOT NULL,
    target_note_id BIGINT NOT NULL,

    PRIMARY KEY (source_note_id, target_note_id),

    CONSTRAINT fk_source_note_to_note FOREIGN KEY (source_note_id) REFERENCES TB_NOTE(note_id) ON DELETE CASCADE,
    CONSTRAINT fk_target_note_to_note FOREIGN KEY (target_note_id) REFERENCES TB_NOTE(note_id) ON DELETE CASCADE,

    CONSTRAINT chk_no_self_link CHECK (source_note_id <> target_note_id)
);