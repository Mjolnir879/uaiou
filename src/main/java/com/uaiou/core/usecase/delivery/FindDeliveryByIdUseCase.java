package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Delivery;
import com.uaiou.core.domain.gateway.DeliveryGateway;
import com.uaiou.core.domain.exception.DomainException;

import java.util.Optional;
import java.util.UUID;

public class FindDeliveryByIdUseCase {

    private final DeliveryGateway deliveryGateway;

    public FindDeliveryByIdUseCase(DeliveryGateway deliveryGateway) {
        this.deliveryGateway = deliveryGateway;
    }

    public Optional<Delivery> execute(UUID id) {
        return deliveryGateway.findById(id);
    }
}
