package com.uaiou.core.domain.gateway;

import com.uaiou.core.domain.entity.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderGateway {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findAll(UUID establishmentId, Boolean delivered, com.uaiou.core.domain.entity.DeliveryStatusEnum status);
    void delete(UUID id);
    List<Order> findAllByIds(List<UUID> ids);
}
