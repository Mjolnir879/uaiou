package com.uaiou.infrastructure.persistence.gateway;

import com.uaiou.core.domain.entity.DeliveryPerson;
import com.uaiou.core.domain.gateway.DeliveryPersonGateway;
import com.uaiou.infrastructure.persistence.entity.DeliveryPersonEntity;
import com.uaiou.infrastructure.persistence.mapper.DeliveryPersonPersistenceMapper;
import com.uaiou.infrastructure.persistence.repository.DeliveryPersonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DeliveryPersonGatewayImpl implements DeliveryPersonGateway {

    private final DeliveryPersonJpaRepository repository;
    private final DeliveryPersonPersistenceMapper mapper;

    public DeliveryPersonGatewayImpl(DeliveryPersonJpaRepository repository,
                                      DeliveryPersonPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DeliveryPerson save(DeliveryPerson deliveryPerson) {
        DeliveryPersonEntity entity = mapper.toEntity(deliveryPerson);
        DeliveryPersonEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<DeliveryPerson> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
