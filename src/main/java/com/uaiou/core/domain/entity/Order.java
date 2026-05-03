package com.uaiou.core.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private UUID id;
    private Integer number;
    private String name;
    private OrderSpecificsEnum specifics;
    private Address address;
    private Establishment establishment;
    private UUID deliveryId; // Reference to delivery to avoid circular dependency in domain if needed
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private boolean delivered;
    private OrderType type;

    private Order(UUID id, Integer number, String name, OrderSpecificsEnum specifics, Address address, Establishment establishment, UUID deliveryId, LocalDateTime createdAt, LocalDateTime deliveredAt, boolean delivered, OrderType type) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.specifics = specifics;
        this.address = address;
        this.establishment = establishment;
        this.deliveryId = deliveryId;
        this.createdAt = createdAt;
        this.deliveredAt = deliveredAt;
        this.delivered = delivered;
        this.type = type;
    }

    public static Order create(String name, OrderSpecificsEnum specifics, Address address, Establishment establishment, OrderType type) {
        validate(name, specifics, address, establishment, type);
        return new Order(
                UUID.randomUUID(),
                null, // Should be set by persistence or use case
                name,
                specifics,
                address,
                establishment,
                null,
                LocalDateTime.now(),
                null,
                false,
                type
        );
    }

    public static Order reconstitute(UUID id, Integer number, String name, OrderSpecificsEnum specifics, Address address, Establishment establishment, UUID deliveryId, LocalDateTime createdAt, LocalDateTime deliveredAt, boolean delivered, OrderType type) {
        return new Order(id, number, name, specifics, address, establishment, deliveryId, createdAt, deliveredAt, delivered, type);
    }

    private static void validate(String name, OrderSpecificsEnum specifics, Address address, Establishment establishment, OrderType type) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Order name is required");
        if (specifics == null) throw new IllegalArgumentException("Order specifics is required");
        if (address == null) throw new IllegalArgumentException("Order address is required");
        if (establishment == null) throw new IllegalArgumentException("Order establishment is required");
        if (type == null) throw new IllegalArgumentException("Order type is required");
    }

    public void markAsDelivered() {
        this.delivered = true;
        this.deliveredAt = LocalDateTime.now();
    }

    public void markAsNotDelivered() {
        this.delivered = false;
        this.deliveredAt = null;
    }

    public Order update(String name, OrderSpecificsEnum specifics, UUID deliveryId) {
        final String updatedName = (name != null && !name.isBlank()) ? name : this.name;
        final OrderSpecificsEnum updatedSpecifics = specifics != null ? specifics : this.specifics;

        return Order.reconstitute(
                this.id,
                this.number,
                updatedName,
                updatedSpecifics,
                this.address,
                this.establishment,
                deliveryId != null ? deliveryId : this.deliveryId,
                this.createdAt,
                this.deliveredAt,
                this.delivered,
                this.type
        );
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    // Getters
    public UUID getId() { return id; }
    public Integer getNumber() { return number; }
    public String getName() { return name; }
    public OrderSpecificsEnum getSpecifics() { return specifics; }
    public Address getAddress() { return address; }
    public Establishment getEstablishment() { return establishment; }
    public UUID getDeliveryId() { return deliveryId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public boolean isDelivered() { return delivered; }
    public OrderType getType() { return type; }
}
