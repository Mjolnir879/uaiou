package com.uaiou.core.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Evaluation {
    private UUID id;
    private BigDecimal rating;
    private String note;
    private LocalDateTime createdAt;
    private Establishment establishment;
    private DeliveryPerson deliveryPerson;
    private EvaluationTypeEnum type;

    private Evaluation(UUID id, BigDecimal rating, String note, LocalDateTime createdAt, Establishment establishment, DeliveryPerson deliveryPerson, EvaluationTypeEnum type) {
        this.id = id;
        this.rating = rating;
        this.note = note;
        this.createdAt = createdAt;
        this.establishment = establishment;
        this.deliveryPerson = deliveryPerson;
        this.type = type;
    }

    public static Evaluation create(BigDecimal rating, String note, Establishment establishment, DeliveryPerson deliveryPerson, EvaluationTypeEnum type) {
        validate(rating, note, establishment, deliveryPerson, type);
        return new Evaluation(
                UUID.randomUUID(),
                rating,
                note,
                LocalDateTime.now(),
                establishment,
                deliveryPerson,
                type
        );
    }

    public static Evaluation reconstitute(UUID id, BigDecimal rating, String note, LocalDateTime createdAt, Establishment establishment, DeliveryPerson deliveryPerson, EvaluationTypeEnum type) {
        return new Evaluation(id, rating, note, createdAt, establishment, deliveryPerson, type);
    }

    private static void validate(BigDecimal rating, String note, Establishment establishment, DeliveryPerson deliveryPerson, EvaluationTypeEnum type) {
        if (rating == null || rating.compareTo(BigDecimal.ONE) < 0 || rating.compareTo(new BigDecimal("5.0")) > 0) {
            throw new IllegalArgumentException("Rating must be between 1.0 and 5.0");
        }
        if (note == null || note.isBlank()) throw new IllegalArgumentException("Note is required");
        if (type == null) throw new IllegalArgumentException("Evaluation type is required");
        if (type == EvaluationTypeEnum.ESTABLISHMENT_EVALUATION && establishment == null) {
            throw new IllegalArgumentException("Establishment is required for establishment evaluation");
        }
        if (type == EvaluationTypeEnum.DELIVERY_EVALUATION && deliveryPerson == null) {
            throw new IllegalArgumentException("Delivery person is required for delivery evaluation");
        }
    }

    // Getters
    public UUID getId() { return id; }
    public BigDecimal getRating() { return rating; }
    public String getNote() { return note; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Establishment getEstablishment() { return establishment; }
    public DeliveryPerson getDeliveryPerson() { return deliveryPerson; }
    public EvaluationTypeEnum getType() { return type; }
}
