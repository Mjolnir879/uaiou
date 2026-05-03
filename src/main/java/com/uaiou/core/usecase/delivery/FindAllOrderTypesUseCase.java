package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.OrderType;
import com.uaiou.core.domain.gateway.OrderTypeGateway;

import java.util.List;

public class FindAllOrderTypesUseCase {

    private final OrderTypeGateway orderTypeGateway;

    public FindAllOrderTypesUseCase(OrderTypeGateway orderTypeGateway) {
        this.orderTypeGateway = orderTypeGateway;
    }

    public List<OrderType> execute() {
        return orderTypeGateway.findAll();
    }
}
