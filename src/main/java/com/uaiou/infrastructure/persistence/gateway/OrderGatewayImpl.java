package com.uaiou.infrastructure.persistence.gateway;

import com.uaiou.core.domain.entity.Order;
import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.domain.gateway.OrderGateway;
import com.uaiou.infrastructure.persistence.entity.OrderEntity;
import com.uaiou.infrastructure.persistence.mapper.OrderPersistenceMapper;
import com.uaiou.infrastructure.persistence.repository.OrderJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderGatewayImpl implements OrderGateway {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderPersistenceMapper mapper;

    public OrderGatewayImpl(OrderJpaRepository orderJpaRepository, OrderPersistenceMapper mapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Order save(Order order) {
        if (order.getNumber() == null) {
            Integer lastNumber = orderJpaRepository.findTopByOrderByNumberDesc()
                    .map(OrderEntity::getNumber)
                    .orElse(0);
            order.setNumber(lastNumber + 1);
        }

        OrderEntity entity = mapper.toEntity(order);
        OrderEntity savedEntity = orderJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Order> findAll(UUID establishmentId, Boolean delivered, DeliveryStatusEnum status) {
        if (establishmentId != null && delivered != null && status != null) {
            return orderJpaRepository.findByEstablishmentIdAndDeliveredAndDeliveryStatus(establishmentId, delivered, status).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }
        if (establishmentId != null && delivered != null) {
            return orderJpaRepository.findByEstablishmentIdAndDelivered(establishmentId, delivered).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }
        if (establishmentId != null && status != null) {
            return orderJpaRepository.findByEstablishmentIdAndDeliveryStatus(establishmentId, status).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }
        if (delivered != null && status != null) {
            return orderJpaRepository.findByDeliveredAndDeliveryStatus(delivered, status).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }
        if (establishmentId != null) {
            return orderJpaRepository.findByEstablishmentId(establishmentId).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }
        if (delivered != null) {
            return orderJpaRepository.findByDelivered(delivered).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }
        if (status != null) {
            return orderJpaRepository.findByDeliveryStatus(status).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }

        return orderJpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        orderJpaRepository.deleteById(id);
    }

    @Override
    public List<Order> findAllByIds(List<UUID> ids) {
        return orderJpaRepository.findAllById(ids).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
