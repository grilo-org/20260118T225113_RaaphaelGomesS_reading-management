CREATE TABLE IF NOT EXISTS TB_SUGGESTION_TEMPLATE(
    suggestion_template_id BIGSERIAL PRIMARY KEY,
    suggested_isbn varchar(255),
    suggested_title VARCHAR(255) NOT NULL,
    suggested_author varchar(255) NOT NULL,
    suggested_edition VARCHAR(50),
    suggested_publisher VARCHAR(100),
    suggested_description VARCHAR(255),
    suggested_publication_year INT,
    suggested_cover VARCHAR(255),
    suggested_pages INT,
    reason varchar(255),
    justification varchar (255),
    status varchar(1000),
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reviewed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_TB_SUGGESTION_TEMPLATE_status CHECK (status in ('APPROVE', 'DECLINE', 'IN_ANALYZE')),

    CONSTRAINT fk_st_to_user FOREIGN KEY (user_id) REFERENCES TB_USER (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TB_SUGGESTION_CATEGORY_NAMES(
    suggestion_template_id BIGINT NOT NULL,
    name varchar(100) NOT NULL UNIQUE,

    PRIMARY KEY (suggestion_template_id, name),

    CONSTRAINT fk_scn_to_suggestion_template FOREIGN KEY (suggestion_template_id) REFERENCES TB_SUGGESTION_TEMPLATE (suggestion_template_id) ON DELETE CASCADE
);