-- =====================================================
-- V1: Initial schema for UAiOU Delivery Backend
-- =====================================================

-- Enable UUID extension (PostgreSQL)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =====================================================
-- USERS
-- =====================================================
CREATE TABLE users (
    id           UUID             PRIMARY KEY DEFAULT gen_random_uuid(),
    username     VARCHAR(150)     NOT NULL,
    email        VARCHAR(255)     NOT NULL,
    phone_number VARCHAR(20),
    active       BOOLEAN          NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP        NOT NULL DEFAULT NOW(),

    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE INDEX idx_users_email ON users (email);

-- =====================================================
-- ESTABLISHMENTS (must be created before addresses)
-- =====================================================
CREATE TABLE establishments (
    id            UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(255)      NOT NULL,
    profile_image VARCHAR(500),
    fiscal_code   VARCHAR(20)       NOT NULL,
    rating        DOUBLE PRECISION  DEFAULT 0,
    owner_id      UUID              NOT NULL,

    CONSTRAINT uk_establishments_fiscal_code UNIQUE (fiscal_code),

    CONSTRAINT fk_establishments_owner
        FOREIGN KEY (owner_id) REFERENCES users (id)
        ON DELETE CASCADE
);

-- =====================================================
-- ADDRESSES
-- =====================================================
CREATE TABLE addresses (
    id               UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
    street           VARCHAR(255)      NOT NULL,
    number           VARCHAR(20),
    complement       VARCHAR(100),
    neighborhood     VARCHAR(100),
    city             VARCHAR(100)      NOT NULL,
    state            VARCHAR(2)        NOT NULL,
    zip_code         VARCHAR(10),
    latitude         DOUBLE PRECISION,
    longitude        DOUBLE PRECISION,
    establishment_id UUID,

    CONSTRAINT fk_addresses_establishment
        FOREIGN KEY (establishment_id) REFERENCES establishments (id)
        ON DELETE SET NULL
);

-- =====================================================
-- DELIVERY PERSONS
-- =====================================================
CREATE TABLE delivery_persons (
    id           UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
    rating       DOUBLE PRECISION  DEFAULT 0,
    fiscal_code  VARCHAR(20)       NOT NULL,
    cnh_code     VARCHAR(20)       NOT NULL,
    cnh_document VARCHAR(255),
    user_id      UUID              NOT NULL,
    address_id   UUID,

    CONSTRAINT uk_delivery_persons_fiscal_code UNIQUE (fiscal_code),
    CONSTRAINT uk_delivery_persons_cnh_code    UNIQUE (cnh_code),
    CONSTRAINT uk_delivery_persons_user_id     UNIQUE (user_id),

    CONSTRAINT fk_delivery_persons_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_delivery_persons_address
        FOREIGN KEY (address_id) REFERENCES addresses (id)
        ON DELETE SET NULL
);
