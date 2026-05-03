package com.uaiou.infrastructure.persistence.mapper;

import com.uaiou.core.domain.entity.OrderType;
import com.uaiou.infrastructure.persistence.entity.OrderTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderTypePersistenceMapper {

    public OrderType toDomain(OrderTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        return OrderType.create(entity.getCode());
    }

    public OrderTypeEntity toEntity(OrderType domain) {
        if (domain == null) {
            return null;
        }
        return OrderTypeEntity.builder()
                .code(domain.getCode())
                .build();
    }
}
