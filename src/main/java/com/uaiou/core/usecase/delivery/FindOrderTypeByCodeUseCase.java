package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.OrderType;
import com.uaiou.core.domain.gateway.OrderTypeGateway;

import java.util.Optional;

public class FindOrderTypeByCodeUseCase {

    private final OrderTypeGateway orderTypeGateway;

    public FindOrderTypeByCodeUseCase(OrderTypeGateway orderTypeGateway) {
        this.orderTypeGateway = orderTypeGateway;
    }

    public Optional<OrderType> execute(String code) {
        return orderTypeGateway.findByCode(code);
    }
}
