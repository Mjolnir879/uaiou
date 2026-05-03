-- =====================================================
-- V3: Delivery Core Schema
-- =====================================================

-- Order Types Table
CREATE TABLE order_types (
    code VARCHAR(50) PRIMARY KEY
);

-- Seed Order Types
INSERT INTO order_types (code) VALUES ('FOOD'), ('PHARMACY'), ('GROCERY'), ('OTHER');

-- Orders Table
CREATE TABLE orders (
    id               UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
    number           SERIAL,
    name             VARCHAR(255)      NOT NULL,
    specifics        VARCHAR(50)       NOT NULL,
    address_id       UUID              NOT NULL,
    establishment_id UUID              NOT NULL,
    delivery_id      UUID,
    created_at       TIMESTAMP         NOT NULL DEFAULT NOW(),
    delivered_at     TIMESTAMP,
    delivered        BOOLEAN           NOT NULL DEFAULT FALSE,
    type_code        VARCHAR(50)       NOT NULL,

    CONSTRAINT fk_orders_address
        FOREIGN KEY (address_id) REFERENCES addresses (id),

    CONSTRAINT fk_orders_establishment
        FOREIGN KEY (establishment_id) REFERENCES establishments (id),

    CONSTRAINT fk_orders_type
        FOREIGN KEY (type_code) REFERENCES order_types (code)
);

-- Deliveries Table
CREATE TABLE deliveries (
    id                       UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
    number                   SERIAL,
    delivery_person_id       UUID              NOT NULL,
    establishment_id         UUID              NOT NULL,
    address_id               UUID              NOT NULL,
    created_at               TIMESTAMP         NOT NULL DEFAULT NOW(),
    delivered_at             TIMESTAMP,
    is_finished              BOOLEAN           NOT NULL DEFAULT FALSE,
    status                   VARCHAR(50)       NOT NULL,
    value                    DECIMAL(10, 2)    NOT NULL,
    paid                     BOOLEAN           NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_deliveries_person
        FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons (id),

    CONSTRAINT fk_deliveries_establishment
        FOREIGN KEY (establishment_id) REFERENCES establishments (id),

    CONSTRAINT fk_deliveries_address
        FOREIGN KEY (address_id) REFERENCES addresses (id)
);

-- Update orders to link to deliveries
ALTER TABLE orders ADD CONSTRAINT fk_orders_delivery
    FOREIGN KEY (delivery_id) REFERENCES deliveries (id)
    ON DELETE SET NULL;

-- Evaluations Table
CREATE TABLE evaluations (
    id                 UUID              PRIMARY KEY DEFAULT gen_random_uuid(),
    rating             DECIMAL(3, 2)     NOT NULL,
    note               TEXT              NOT NULL,
    created_at         TIMESTAMP         NOT NULL DEFAULT NOW(),
    establishment_id   UUID,
    delivery_person_id UUID,
    type               VARCHAR(50)       NOT NULL,

    CONSTRAINT fk_evaluations_establishment
        FOREIGN KEY (establishment_id) REFERENCES establishments (id),

    CONSTRAINT fk_evaluations_person
        FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons (id),

    CONSTRAINT ck_evaluation_rating
        CHECK (rating >= 1.0 AND rating <= 5.0)
);
