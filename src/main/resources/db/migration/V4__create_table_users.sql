CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       user_type VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       activate BOOLEAN DEFAULT true
);

INSERT INTO public.users (id, email, password, user_type, created_at, activate)
VALUES (
           gen_random_uuid(),
           'admin@siplan.com.br',
           '$2a$12$G90LY/739Fj0sxxyFMWObOHELNXXfTFnj3WxH5UPmOPngVi/nKOci',
           'ADMIN',
           CURRENT_TIMESTAMP,
           true
       );

INSERT INTO public.users (id, email, password, user_type, created_at, activate)
VALUES (
           gen_random_uuid(),
           'user@siplan.com.br',
           '$2a$12$G90LY/739Fj0sxxyFMWObOHELNXXfTFnj3WxH5UPmOPngVi/nKOci',
           'USER',
           CURRENT_TIMESTAMP,
           true
       );