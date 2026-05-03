package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Delivery;
import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.domain.gateway.DeliveryGateway;

import java.util.List;
import java.util.UUID;

public class ListDeliveriesUseCase {

    private final DeliveryGateway deliveryGateway;

    public ListDeliveriesUseCase(DeliveryGateway deliveryGateway) {
        this.deliveryGateway = deliveryGateway;
    }

    public List<Delivery> execute(UUID deliveryPersonId, UUID establishmentId, DeliveryStatusEnum status) {
        return deliveryGateway.findAll(deliveryPersonId, establishmentId, status);
    }
}
