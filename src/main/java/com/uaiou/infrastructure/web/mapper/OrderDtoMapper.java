package com.uaiou.infrastructure.web.mapper;

import com.uaiou.core.domain.entity.Address;
import com.uaiou.core.domain.entity.Establishment;
import com.uaiou.core.domain.entity.Order;
import com.uaiou.core.domain.entity.OrderType;
import com.uaiou.infrastructure.web.dto.request.CreateOrderRequest;
import com.uaiou.infrastructure.web.dto.response.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OrderDtoMapper {

    public Order toDomain(CreateOrderRequest request, Address address, Establishment establishment, OrderType orderType) {
        return Order.create(
                request.name(),
                request.specifics(),
                address,
                establishment,
                orderType
        );
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getNumber(),
                order.getName(),
                order.getSpecifics(),
                order.getAddress().getId(),
                order.getEstablishment().getId(),
                order.getDeliveryId(),
                order.getCreatedAt(),
                order.getDeliveredAt(),
                order.isDelivered(),
                order.getType().getCode()
        );
    }
}
