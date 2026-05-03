package com.uaiou.infrastructure.persistence.gateway;

import com.uaiou.core.domain.entity.OrderType;
import com.uaiou.core.domain.gateway.OrderTypeGateway;
import com.uaiou.infrastructure.persistence.mapper.OrderTypePersistenceMapper;
import com.uaiou.infrastructure.persistence.repository.OrderTypeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderTypeGatewayImpl implements OrderTypeGateway {

    private final OrderTypeJpaRepository orderTypeJpaRepository;
    private final OrderTypePersistenceMapper mapper;

    public OrderTypeGatewayImpl(OrderTypeJpaRepository orderTypeJpaRepository, OrderTypePersistenceMapper mapper) {
        this.orderTypeJpaRepository = orderTypeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<OrderType> findAll() {
        return orderTypeJpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderType> findByCode(String code) {
        return orderTypeJpaRepository.findById(code)
                .map(mapper::toDomain);
    }
}
