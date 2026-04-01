CREATE TABLE versions (
                          id UUID PRIMARY KEY,
                          date_release DATE,
                          number_version VARCHAR(50),
                          changelog TEXT,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          activate BOOLEAN NOT NULL
);