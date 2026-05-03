package com.uaiou.infrastructure.web.mapper;

import com.uaiou.core.domain.entity.*;
import com.uaiou.infrastructure.web.dto.request.CreateDeliveryRequest;
import com.uaiou.infrastructure.web.dto.response.DeliveryResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryDtoMapper {

    public Delivery toDomain(CreateDeliveryRequest request, DeliveryPerson deliveryPerson, Establishment establishment, List<Order> orders, Address address) {
        return Delivery.create(
                deliveryPerson,
                establishment,
                orders,
                address,
                request.value()
        );
    }

    public DeliveryResponse toResponse(Delivery delivery) {
        return new DeliveryResponse(
                delivery.getId(),
                delivery.getNumber(),
                delivery.getDeliveryPerson().getId(),
                delivery.getDeliveryEstablishment().getId(),
                delivery.getDeliveryOrders().stream().map(Order::getId).collect(Collectors.toList()),
                delivery.getDeliveryAddress().getId(),
                delivery.getCreatedAt(),
                delivery.getDeliveredAt(),
                delivery.isFinished(),
                delivery.getStatus(),
                delivery.getValue(),
                delivery.isPaid()
        );
    }
}
