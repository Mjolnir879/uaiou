package com.uaiou.infrastructure.persistence.gateway;

import com.uaiou.core.domain.entity.Address;
import com.uaiou.core.domain.gateway.AddressGateway;
import com.uaiou.infrastructure.persistence.mapper.AddressPersistenceMapper;
import com.uaiou.infrastructure.persistence.repository.AddressJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AddressGatewayImpl implements AddressGateway {

    private final AddressJpaRepository repository;
    private final AddressPersistenceMapper mapper;

    public AddressGatewayImpl(AddressJpaRepository repository, AddressPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Address> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
