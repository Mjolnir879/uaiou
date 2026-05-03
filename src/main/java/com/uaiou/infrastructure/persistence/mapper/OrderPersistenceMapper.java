package com.uaiou.infrastructure.persistence.mapper;

import com.uaiou.core.domain.entity.Order;
import com.uaiou.infrastructure.persistence.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceMapper {

    private final AddressPersistenceMapper addressMapper;
    private final EstablishmentPersistenceMapper establishmentMapper;
    private final OrderTypePersistenceMapper orderTypeMapper;

    public OrderPersistenceMapper(AddressPersistenceMapper addressMapper, EstablishmentPersistenceMapper establishmentMapper, OrderTypePersistenceMapper orderTypeMapper) {
        this.addressMapper = addressMapper;
        this.establishmentMapper = establishmentMapper;
        this.orderTypeMapper = orderTypeMapper;
    }

    public Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        return Order.reconstitute(
                entity.getId(),
                entity.getNumber(),
                entity.getName(),
                entity.getSpecifics(),
                addressMapper.toDomain(entity.getAddress()),
                establishmentMapper.toDomain(entity.getEstablishment()),
                entity.getDelivery() != null ? entity.getDelivery().getId() : null,
                entity.getCreatedAt(),
                entity.getDeliveredAt(),
                entity.isDelivered(),
                orderTypeMapper.toDomain(entity.getType())
        );
    }

    public OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }
        return OrderEntity.builder()
                .id(domain.getId())
                .number(domain.getNumber())
                .name(domain.getName())
                .specifics(domain.getSpecifics())
                .address(addressMapper.toEntity(domain.getAddress()))
                .establishment(establishmentMapper.toEntity(domain.getEstablishment()))
                .createdAt(domain.getCreatedAt())
                .deliveredAt(domain.getDeliveredAt())
                .delivered(domain.isDelivered())
                .type(orderTypeMapper.toEntity(domain.getType()))
                .build();
    }
}
