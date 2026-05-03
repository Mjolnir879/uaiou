package com.uaiou.core.domain.gateway;

import com.uaiou.core.domain.entity.Delivery;
import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryGateway {
    Delivery save(Delivery delivery);
    Optional<Delivery> findById(UUID id);
    List<Delivery> findAll(UUID deliveryPersonId, UUID establishmentId, DeliveryStatusEnum status);
}
