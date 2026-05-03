package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.gateway.OrderGateway;
import com.uaiou.core.domain.entity.Order;
import com.uaiou.core.domain.entity.OrderSpecificsEnum;
import com.uaiou.core.domain.exception.DomainException;

import java.util.UUID;

public class UpdateOrderUseCase {

    private final OrderGateway orderGateway;

    public UpdateOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Order execute(UUID id, String name, OrderSpecificsEnum specifics, UUID deliveryId) {
        Order order = orderGateway.findById(id)
                .orElseThrow(() -> new DomainException("Order not found"));

        Order updatedOrder = order.update(name, specifics, deliveryId);

        return orderGateway.save(updatedOrder);
    }
}
