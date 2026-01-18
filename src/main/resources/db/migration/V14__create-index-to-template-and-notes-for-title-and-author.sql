CREATE INDEX IF NOT EXISTS idx_book_template_title_lower
ON TB_BOOK_TEMPLATE (LOWER(title));

CREATE INDEX IF NOT EXISTS idx_book_template_author_lower
ON TB_BOOK_TEMPLATE (LOWER(author));

CREATE INDEX IF NOT EXISTS idx_note_title_search
ON TB_NOTE (LOWER(title));