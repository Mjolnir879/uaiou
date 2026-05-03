package com.uaiou.core.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Delivery {
    private UUID id;
    private Integer number;
    private DeliveryPerson deliveryPerson;
    private Establishment deliveryEstablishment;
    private List<Order> deliveryOrders;
    private Address deliveryAddress;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private boolean isFinished;
    private DeliveryStatusEnum status;
    private BigDecimal value;
    private boolean paid;

    private Delivery(UUID id, Integer number, DeliveryPerson deliveryPerson, Establishment deliveryEstablishment, List<Order> deliveryOrders, Address deliveryAddress, LocalDateTime createdAt, LocalDateTime deliveredAt, boolean isFinished, DeliveryStatusEnum status, BigDecimal value, boolean paid) {
        this.id = id;
        this.number = number;
        this.deliveryPerson = deliveryPerson;
        this.deliveryEstablishment = deliveryEstablishment;
        this.deliveryOrders = deliveryOrders != null ? new ArrayList<>(deliveryOrders) : new ArrayList<>();
        this.deliveryAddress = deliveryAddress;
        this.createdAt = createdAt;
        this.deliveredAt = deliveredAt;
        this.isFinished = isFinished;
        this.status = status;
        this.value = value;
        this.paid = paid;
    }

    public static Delivery create(DeliveryPerson deliveryPerson, Establishment deliveryEstablishment, List<Order> orders, Address deliveryAddress, BigDecimal value) {
        validate(deliveryPerson, deliveryEstablishment, orders, deliveryAddress, value);
        return new Delivery(
                UUID.randomUUID(),
                null,
                deliveryPerson,
                deliveryEstablishment,
                orders,
                deliveryAddress,
                LocalDateTime.now(),
                null,
                false,
                DeliveryStatusEnum.PENDING,
                value,
                false
        );
    }

    public static Delivery reconstitute(UUID id, Integer number, DeliveryPerson deliveryPerson, Establishment deliveryEstablishment, List<Order> deliveryOrders, Address deliveryAddress, LocalDateTime createdAt, LocalDateTime deliveredAt, boolean isFinished, DeliveryStatusEnum status, BigDecimal value, boolean paid) {
        return new Delivery(id, number, deliveryPerson, deliveryEstablishment, deliveryOrders, deliveryAddress, createdAt, deliveredAt, isFinished, status, value, paid);
    }

    private static void validate(DeliveryPerson deliveryPerson, Establishment establishment, List<Order> orders, Address address, BigDecimal value) {
        if (deliveryPerson == null) throw new IllegalArgumentException("Delivery person is required");
        if (establishment == null) throw new IllegalArgumentException("Establishment is required");
        if (orders == null || orders.isEmpty()) throw new IllegalArgumentException("At least one order is required");
        if (address == null) throw new IllegalArgumentException("Delivery address is required");
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Valid delivery value is required");
    }

    public void updateStatus(DeliveryStatusEnum newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Delivery status is required");
        }
        this.status = newStatus;

        if (newStatus == DeliveryStatusEnum.FINISHED) {
            this.isFinished = true;
            this.deliveredAt = LocalDateTime.now();
            this.deliveryOrders.forEach(Order::markAsDelivered);
            return;
        }

        this.isFinished = false;
        this.deliveredAt = null;
    }

    public void markAsPaid() {
        if (!this.isFinished || this.status != DeliveryStatusEnum.FINISHED) {
            throw new IllegalStateException("Only finished deliveries can be marked as paid");
        }
        this.paid = true;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    // Getters
    public UUID getId() { return id; }
    public Integer getNumber() { return number; }
    public DeliveryPerson getDeliveryPerson() { return deliveryPerson; }
    public Establishment getDeliveryEstablishment() { return deliveryEstablishment; }
    public List<Order> getDeliveryOrders() { return Collections.unmodifiableList(deliveryOrders); }
    public Address getDeliveryAddress() { return deliveryAddress; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public boolean isFinished() { return isFinished; }
    public DeliveryStatusEnum getStatus() { return status; }
    public BigDecimal getValue() { return value; }
    public boolean isPaid() { return paid; }
}
