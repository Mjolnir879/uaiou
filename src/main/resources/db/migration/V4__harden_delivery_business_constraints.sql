-- =====================================================
-- V4: Harden delivery-core business constraints
-- =====================================================

ALTER TABLE deliveries
    ALTER COLUMN value TYPE DECIMAL(5, 2);

ALTER TABLE orders
    ADD CONSTRAINT ck_orders_specifics
        CHECK (specifics IN ('THERMICCOLD', 'THERMICHOT'));

ALTER TABLE orders
    ADD CONSTRAINT ck_orders_delivered_at
        CHECK (delivered_at IS NULL OR delivered = TRUE);

ALTER TABLE deliveries
    ADD CONSTRAINT ck_deliveries_status
        CHECK (status IN ('PENDING', 'FINISHED', 'CANCELED', 'NOT_DELIVERED', 'AVAILABLE'));

ALTER TABLE deliveries
    ADD CONSTRAINT ck_deliveries_finished_status
        CHECK (is_finished = FALSE OR status = 'FINISHED');

ALTER TABLE deliveries
    ADD CONSTRAINT ck_deliveries_delivered_at_status
        CHECK (delivered_at IS NULL OR status = 'FINISHED');

ALTER TABLE evaluations
    ADD CONSTRAINT ck_evaluations_type
        CHECK (type IN ('ESTABLISHMENT_EVALUATION', 'DELIVERY_EVALUATION'));

ALTER TABLE evaluations
    ADD CONSTRAINT ck_evaluations_target_by_type
        CHECK (
            (type = 'ESTABLISHMENT_EVALUATION' AND establishment_id IS NOT NULL)
            OR
            (type = 'DELIVERY_EVALUATION' AND delivery_person_id IS NOT NULL)
        );
