package com.uaiou.core.domain.gateway;

import com.uaiou.core.domain.entity.OrderType;
import java.util.List;
import java.util.Optional;

public interface OrderTypeGateway {
    List<OrderType> findAll();
    Optional<OrderType> findByCode(String code);
}
