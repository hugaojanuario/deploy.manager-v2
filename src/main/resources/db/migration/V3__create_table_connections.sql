CREATE TABLE connections (
                             id UUID PRIMARY KEY,
                             client_id UUID NOT NULL,
                             user_machine VARCHAR(255),
                             password_machine VARCHAR(255),
                             user_db VARCHAR(255),
                             password_db VARCHAR(255),
                             id_remote_connection VARCHAR(255),
                             password_remote_connection VARCHAR(255),
                             connection_type VARCHAR(50),
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             activate BOOLEAN NOT NULL,

                             CONSTRAINT fk_connection_client
                                 FOREIGN KEY (client_id)
                                     REFERENCES clients(id)
                                     ON DELETE RESTRICT
);