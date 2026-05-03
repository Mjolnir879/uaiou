package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Delivery;
import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.domain.gateway.DeliveryGateway;
import com.uaiou.core.domain.gateway.OrderGateway;
import com.uaiou.core.domain.exception.DomainException;

import java.util.UUID;

public class UpdateDeliveryStatusUseCase {

    private final DeliveryGateway deliveryGateway;
    private final OrderGateway orderGateway;

    public UpdateDeliveryStatusUseCase(DeliveryGateway deliveryGateway, OrderGateway orderGateway) {
        this.deliveryGateway = deliveryGateway;
        this.orderGateway = orderGateway;
    }

    public Delivery execute(UUID id, DeliveryStatusEnum newStatus) {
        Delivery delivery = deliveryGateway.findById(id)
                .orElseThrow(() -> new DomainException("Delivery not found"));

        delivery.updateStatus(newStatus);

        // Handle linked orders when delivery is cancelled or not delivered
        if (newStatus == DeliveryStatusEnum.CANCELED || newStatus == DeliveryStatusEnum.NOT_DELIVERED) {
            delivery.getDeliveryOrders().forEach(order -> {
                order.markAsNotDelivered();
                order.setDeliveryId(null); // Unlink order from delivery
                orderGateway.save(order);
            });
        }

        if (newStatus == DeliveryStatusEnum.PENDING || newStatus == DeliveryStatusEnum.AVAILABLE) {
            delivery.getDeliveryOrders().forEach(order -> {
                order.markAsNotDelivered();
                orderGateway.save(order);
            });
        }

        return deliveryGateway.save(delivery);
    }
}
