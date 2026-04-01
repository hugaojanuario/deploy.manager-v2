CREATE TABLE clients (
                         id UUID PRIMARY KEY,
                         version_id UUID NOT NULL,
                         name VARCHAR(255),
                         city VARCHAR(100),
                         state VARCHAR(50),
                         contact VARCHAR(255),
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         activate BOOLEAN NOT NULL,

                         CONSTRAINT fk_client_version
                             FOREIGN KEY (version_id)
                                 REFERENCES versions(id)
                                 ON DELETE RESTRICT
);