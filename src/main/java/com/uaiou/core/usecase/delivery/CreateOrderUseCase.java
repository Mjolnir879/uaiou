package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Address;
import com.uaiou.core.domain.entity.Establishment;
import com.uaiou.core.domain.entity.Order;
import com.uaiou.core.domain.entity.OrderType;
import com.uaiou.core.domain.gateway.AddressGateway;
import com.uaiou.core.domain.gateway.EstablishmentGateway;
import com.uaiou.core.domain.gateway.OrderGateway;
import com.uaiou.core.domain.gateway.OrderTypeGateway;
import com.uaiou.core.domain.exception.DomainException;

import java.util.UUID;

public class CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final AddressGateway addressGateway;
    private final EstablishmentGateway establishmentGateway;
    private final OrderTypeGateway orderTypeGateway;

    public CreateOrderUseCase(OrderGateway orderGateway, AddressGateway addressGateway, EstablishmentGateway establishmentGateway, OrderTypeGateway orderTypeGateway) {
        this.orderGateway = orderGateway;
        this.addressGateway = addressGateway;
        this.establishmentGateway = establishmentGateway;
        this.orderTypeGateway = orderTypeGateway;
    }

    public CreateOrderOutput execute(CreateOrderInput input) {
        Address address = addressGateway.findById(input.addressId())
                .orElseThrow(() -> new DomainException("Address not found"));
        Establishment establishment = establishmentGateway.findById(input.establishmentId())
                .orElseThrow(() -> new DomainException("Establishment not found"));
        OrderType orderType = orderTypeGateway.findByCode(input.orderTypeCode())
                .orElseThrow(() -> new DomainException("Order type not found"));

        Order order = Order.create(input.name(), input.specifics(), address, establishment, orderType);
        Order savedOrder = orderGateway.save(order);

        return new CreateOrderOutput(
                savedOrder.getId(),
                savedOrder.getNumber(),
                savedOrder.getName(),
                savedOrder.getSpecifics(),
                savedOrder.getAddress().getId(),
                savedOrder.getEstablishment().getId(),
                savedOrder.getDeliveryId(),
                savedOrder.getCreatedAt(),
                savedOrder.getDeliveredAt(),
                savedOrder.isDelivered(),
                savedOrder.getType().getCode()
        );
    }
}
