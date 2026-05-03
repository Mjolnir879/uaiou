package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Delivery;
import com.uaiou.core.domain.gateway.DeliveryGateway;
import com.uaiou.core.domain.exception.DomainException;

import java.util.UUID;

public class UpdateDeliveryPaidStatusUseCase {

    private final DeliveryGateway deliveryGateway;

    public UpdateDeliveryPaidStatusUseCase(DeliveryGateway deliveryGateway) {
        this.deliveryGateway = deliveryGateway;
    }

    public Delivery execute(UUID id) {
        Delivery delivery = deliveryGateway.findById(id)
                .orElseThrow(() -> new DomainException("Delivery not found"));

        try {
            delivery.markAsPaid();
        } catch (IllegalStateException ex) {
            throw new DomainException(ex.getMessage());
        }

        return deliveryGateway.save(delivery);
    }
}
