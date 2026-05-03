package com.uaiou.infrastructure.persistence.mapper;

import com.uaiou.core.domain.entity.Delivery;
import com.uaiou.infrastructure.persistence.entity.DeliveryEntity;
import com.uaiou.infrastructure.persistence.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DeliveryPersistenceMapper {

    private final DeliveryPersonPersistenceMapper deliveryPersonMapper;
    private final EstablishmentPersistenceMapper establishmentMapper;
    private final AddressPersistenceMapper addressMapper;
    private final OrderPersistenceMapper orderMapper;

    public DeliveryPersistenceMapper(DeliveryPersonPersistenceMapper deliveryPersonMapper, EstablishmentPersistenceMapper establishmentMapper, AddressPersistenceMapper addressMapper, OrderPersistenceMapper orderMapper) {
        this.deliveryPersonMapper = deliveryPersonMapper;
        this.establishmentMapper = establishmentMapper;
        this.addressMapper = addressMapper;
        this.orderMapper = orderMapper;
    }

    public Delivery toDomain(DeliveryEntity entity) {
        if (entity == null) {
            return null;
        }
        return Delivery.reconstitute(
                entity.getId(),
                entity.getNumber(),
                deliveryPersonMapper.toDomain(entity.getDeliveryPerson()),
                establishmentMapper.toDomain(entity.getEstablishment()),
                entity.getOrders().stream().map(orderMapper::toDomain).collect(Collectors.toList()),
                addressMapper.toDomain(entity.getAddress()),
                entity.getCreatedAt(),
                entity.getDeliveredAt(),
                entity.isFinished(),
                entity.getStatus(),
                entity.getValue(),
                entity.isPaid()
        );
    }

    public DeliveryEntity toEntity(Delivery domain) {
        if (domain == null) {
            return null;
        }
        DeliveryEntity entity = DeliveryEntity.builder()
                .id(domain.getId())
                .number(domain.getNumber())
                .deliveryPerson(deliveryPersonMapper.toEntity(domain.getDeliveryPerson()))
                .establishment(establishmentMapper.toEntity(domain.getDeliveryEstablishment()))
                .address(addressMapper.toEntity(domain.getDeliveryAddress()))
                .createdAt(domain.getCreatedAt())
                .deliveredAt(domain.getDeliveredAt())
                .isFinished(domain.isFinished())
                .status(domain.getStatus())
                .value(domain.getValue())
                .paid(domain.isPaid())
                .build();

        entity.setOrders(domain.getDeliveryOrders().stream().map(order -> {
            OrderEntity orderEntity = orderMapper.toEntity(order);
            orderEntity.setDelivery(entity);
            return orderEntity;
        }).collect(Collectors.toList()));

        return entity;
    }
}
