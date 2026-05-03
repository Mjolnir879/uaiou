package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Order;
import com.uaiou.core.domain.gateway.OrderGateway;
import com.uaiou.core.domain.exception.DomainException;

import java.util.Optional;
import java.util.UUID;

public class FindOrderByIdUseCase {

    private final OrderGateway orderGateway;

    public FindOrderByIdUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Optional<Order> execute(UUID id) {
        return orderGateway.findById(id);
    }
}
