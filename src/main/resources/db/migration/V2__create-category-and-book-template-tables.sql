CREATE TABLE IF NOT EXISTS TB_CATEGORY(
    category_id BIGSERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS TB_BOOK_TEMPLATE(
    book_template_id BIGSERIAL PRIMARY KEY,
    isbn varchar(255) UNIQUE,
    title_author varchar(255) UNIQUE,
    title VARCHAR(255) NOT NULL,
    author varchar(255) NOT NULL,
    edition VARCHAR(50),
    publisher VARCHAR(100),
    description VARCHAR(255),
    publication_year INT,
    cover_img VARCHAR(255),
    pages INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);