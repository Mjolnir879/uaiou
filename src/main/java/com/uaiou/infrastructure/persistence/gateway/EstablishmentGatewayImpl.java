package com.uaiou.infrastructure.persistence.gateway;

import com.uaiou.core.domain.entity.Establishment;
import com.uaiou.core.domain.gateway.EstablishmentGateway;
import com.uaiou.infrastructure.persistence.entity.EstablishmentEntity;
import com.uaiou.infrastructure.persistence.mapper.EstablishmentPersistenceMapper;
import com.uaiou.infrastructure.persistence.repository.EstablishmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EstablishmentGatewayImpl implements EstablishmentGateway {

    private final EstablishmentJpaRepository repository;
    private final EstablishmentPersistenceMapper mapper;

    public EstablishmentGatewayImpl(EstablishmentJpaRepository repository,
                                     EstablishmentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Establishment save(Establishment establishment) {
        EstablishmentEntity entity = mapper.toEntity(establishment);
        EstablishmentEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Establishment> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
