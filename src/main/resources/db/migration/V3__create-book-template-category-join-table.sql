CREATE TABLE IF NOT EXISTS TB_BOOK_TEMPLATE_CATEGORY(
    book_template_id BIGSERIAL NOT NULL,
    category_id BIGSERIAL NOT NULL,

    PRIMARY KEY(book_template_id, category_id),
    CONSTRAINT fk_btc_to_book_category FOREIGN KEY (book_template_id) REFERENCES TB_BOOK_TEMPLATE(book_template_id) ON DELETE CASCADE,
    CONSTRAINT fk_btc_to_category FOREIGN KEY (category_id) REFERENCES TB_CATEGORY(category_id) ON DELETE CASCADE
);