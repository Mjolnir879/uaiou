package com.uaiou.infrastructure.persistence.gateway;

import com.uaiou.core.domain.entity.User;
import com.uaiou.core.domain.gateway.UserGateway;
import com.uaiou.infrastructure.persistence.entity.UserEntity;
import com.uaiou.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.uaiou.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper mapper;

    public UserGatewayImpl(UserJpaRepository userJpaRepository, UserPersistenceMapper mapper) {
        this.userJpaRepository = userJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
