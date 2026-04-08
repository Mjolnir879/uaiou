package com.uaiou.core.domain.gateway;

import com.uaiou.core.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {

    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
