package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.gateway.OrderGateway;
import com.uaiou.core.domain.exception.DomainException;

import java.util.UUID;

public class DeleteOrderUseCase {

    private final OrderGateway orderGateway;

    public DeleteOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public void execute(UUID id) {
        if (!orderGateway.findById(id).isPresent()) {
            throw new DomainException("Order not found");
        }
        orderGateway.delete(id);
    }
}
