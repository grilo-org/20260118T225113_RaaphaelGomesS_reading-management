CREATE TABLE IF NOT EXISTS TB_BOOK(
    book_id BIGSERIAL PRIMARY KEY,
    status varchar(255),
    read_pages INT DEFAULT 0,
    rating SMALLINT,
    library_id BIGINT NOT NULL,
    book_template_id BIGINT NOT NULL,
    started_at TIMESTAMP,
    finished_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_book_to_library FOREIGN KEY (library_id) REFERENCES TB_LIBRARY(library_id) ON DELETE CASCADE,
    CONSTRAINT fk_book_to_book_template FOREIGN KEY (book_template_id) REFERENCES TB_BOOK_TEMPLATE(book_template_id) ON DELETE RESTRICT,

    CONSTRAINT chk_TB_BOOK_role CHECK (status in ('WANT_TO_READ', 'READING', 'READ', 'DROPPED'))
);

CREATE INDEX idx_TB_BOOK_library_id ON TB_BOOK(library_id);
CREATE INDEX idx_TB_BOOK_book_template_id ON TB_BOOK(book_template_id);