package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Order;
import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.domain.gateway.OrderGateway;

import java.util.List;
import java.util.UUID;

public class ListOrdersUseCase {

    private final OrderGateway orderGateway;

    public ListOrdersUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public List<Order> execute(UUID establishmentId, Boolean delivered, DeliveryStatusEnum status) {
        return orderGateway.findAll(establishmentId, delivered, status);
    }
}
