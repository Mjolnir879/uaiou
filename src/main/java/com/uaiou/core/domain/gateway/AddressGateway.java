package com.uaiou.core.domain.gateway;

import com.uaiou.core.domain.entity.Address;

import java.util.Optional;
import java.util.UUID;

public interface AddressGateway {
    Optional<Address> findById(UUID id);
}
