package com.uaiou.core.usecase.user;

import com.uaiou.core.domain.entity.User;
import com.uaiou.core.domain.gateway.UserGateway;

import java.util.Optional;
import java.util.UUID;

/**
 * Use Case: Find a User by ID.
 */
public class FindUserByIdUseCase {

    private final UserGateway userGateway;

    public FindUserByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Optional<CreateUserOutput> execute(UUID id) {
        return userGateway.findById(id)
                .map(user -> new CreateUserOutput(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.isActive(),
                        user.getCreatedAt()
                ));
    }
}
