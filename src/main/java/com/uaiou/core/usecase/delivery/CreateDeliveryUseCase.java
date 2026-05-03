package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.*;
import com.uaiou.core.domain.exception.DomainException;
import com.uaiou.core.domain.gateway.*;

import java.util.List;
import java.util.UUID;

public class CreateDeliveryUseCase {

    private final DeliveryGateway deliveryGateway;
    private final DeliveryPersonGateway deliveryPersonGateway;
    private final EstablishmentGateway establishmentGateway;
    private final OrderGateway orderGateway;
    private final AddressGateway addressGateway;

    public CreateDeliveryUseCase(DeliveryGateway deliveryGateway, DeliveryPersonGateway deliveryPersonGateway, EstablishmentGateway establishmentGateway, OrderGateway orderGateway, AddressGateway addressGateway) {
        this.deliveryGateway = deliveryGateway;
        this.deliveryPersonGateway = deliveryPersonGateway;
        this.establishmentGateway = establishmentGateway;
        this.orderGateway = orderGateway;
        this.addressGateway = addressGateway;
    }

    public CreateDeliveryOutput execute(CreateDeliveryInput input) {
        DeliveryPerson deliveryPerson = deliveryPersonGateway.findById(input.deliveryPersonId())
                .orElseThrow(() -> new DomainException("Delivery person not found"));
        Establishment establishment = establishmentGateway.findById(input.establishmentId())
                .orElseThrow(() -> new DomainException("Establishment not found"));
        Address address = addressGateway.findById(input.addressId())
                .orElseThrow(() -> new DomainException("Address not found"));
        List<Order> orders = orderGateway.findAllByIds(input.orderIds());

        if (orders.size() != input.orderIds().size()) {
            throw new DomainException("One or more orders not found");
        }

        boolean hasOrderFromAnotherEstablishment = orders.stream()
                .anyMatch(order -> !order.getEstablishment().getId().equals(establishment.getId()));
        if (hasOrderFromAnotherEstablishment) {
            throw new DomainException("All orders must belong to the selected establishment");
        }

        boolean hasAssignedOrder = orders.stream().anyMatch(order -> order.getDeliveryId() != null);
        if (hasAssignedOrder) {
            throw new DomainException("One or more orders are already assigned to a delivery");
        }

        Delivery delivery = Delivery.create(deliveryPerson, establishment, orders, address, input.value());
        Delivery savedDelivery = deliveryGateway.save(delivery);

        for (Order order : orders) {
            order.setDeliveryId(savedDelivery.getId());
            orderGateway.save(order);
        }

        return new CreateDeliveryOutput(
                savedDelivery.getId(),
                savedDelivery.getNumber(),
                savedDelivery.getDeliveryPerson().getId(),
                savedDelivery.getDeliveryEstablishment().getId(),
                savedDelivery.getDeliveryOrders().stream().map(Order::getId).collect(java.util.stream.Collectors.toList()),
                savedDelivery.getDeliveryAddress().getId(),
                savedDelivery.getCreatedAt(),
                savedDelivery.getDeliveredAt(),
                savedDelivery.isFinished(),
                savedDelivery.getStatus(),
                savedDelivery.getValue(),
                savedDelivery.isPaid()
        );
    }
}
