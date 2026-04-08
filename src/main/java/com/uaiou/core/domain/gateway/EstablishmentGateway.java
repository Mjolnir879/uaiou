package com.uaiou.core.domain.gateway;

import com.uaiou.core.domain.entity.Establishment;

import java.util.Optional;
import java.util.UUID;

public interface EstablishmentGateway {

    Establishment save(Establishment establishment);

    Optional<Establishment> findById(UUID id);
}
