package com.uaiou.core.domain.gateway;

import com.uaiou.core.domain.entity.DeliveryPerson;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryPersonGateway {

    DeliveryPerson save(DeliveryPerson deliveryPerson);

    Optional<DeliveryPerson> findById(UUID id);
}
