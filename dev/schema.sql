-- =============================================================
-- Geek Library BC - Database Schema
-- Database: PostgreSQL 16
-- Generated from JPA entities (Spring PhysicalNamingStrategy)
-- =============================================================

-- -------------------------------------------------------------
-- persons
-- Referenced by works (author, illustrator)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS persons (
    id          UUID        PRIMARY KEY,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255)
);

-- -------------------------------------------------------------
-- formats
-- Physical format/size of an edition (e.g. Tankobon, Pocket…)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS formats (
    id          UUID        PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(500),
    width_cm    DOUBLE PRECISION,
    height_cm   DOUBLE PRECISION
);

-- -------------------------------------------------------------
-- works
-- A creative work (manga, novel, etc.)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS works (
    id             UUID        PRIMARY KEY,
    type           VARCHAR(50),               -- WorkType enum
    title          VARCHAR(255),
    description    TEXT,
    author_id      UUID        REFERENCES persons(id),
    illustrator_id UUID        REFERENCES persons(id)
);

-- -------------------------------------------------------------
-- editions
-- A specific publication of a work (language, publisher, format)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS editions (
    id                UUID        PRIMARY KEY,
    work_id           UUID        NOT NULL    REFERENCES works(id),
    publisher         VARCHAR(255),
    language_iso_code VARCHAR(10),
    country_iso_code  VARCHAR(10),
    is_original       BOOLEAN,
    format_id         UUID        REFERENCES formats(id),
    color_mode        VARCHAR(50)             -- ColorMode enum
);

-- -------------------------------------------------------------
-- volumes
-- Individual volumes/chapters belonging to an edition
-- MoneyEmbeddable fields: currency, amount
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS volumes (
    id               UUID        PRIMARY KEY,
    edition_id       UUID        NOT NULL    REFERENCES editions(id),
    title            VARCHAR(255),
    number           INTEGER,
    currency         VARCHAR(10),            -- MoneyEmbeddable.currency
    amount           NUMERIC(19, 4),         -- MoneyEmbeddable.amount
    publication_date DATE,
    isbn             VARCHAR(20),
    pages            INTEGER
);

-- =============================================================
-- Indexes
-- =============================================================
CREATE INDEX IF NOT EXISTS idx_works_author_id      ON works(author_id);
CREATE INDEX IF NOT EXISTS idx_works_illustrator_id ON works(illustrator_id);
CREATE INDEX IF NOT EXISTS idx_editions_work_id     ON editions(work_id);
CREATE INDEX IF NOT EXISTS idx_editions_format_id   ON editions(format_id);
CREATE INDEX IF NOT EXISTS idx_volumes_edition_id   ON volumes(edition_id);
