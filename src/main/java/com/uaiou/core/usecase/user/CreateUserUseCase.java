package com.uaiou.core.usecase.user;

import com.uaiou.core.domain.entity.User;
import com.uaiou.core.domain.exception.EmailAlreadyExistsException;
import com.uaiou.core.domain.gateway.UserGateway;

/**
 * Use Case: Create a new User.
 * Business rule: email must be unique across the system.
 *
 * This class has NO Spring annotations — it is wired via BeanConfig.
 */
public class CreateUserUseCase {

    private final UserGateway userGateway;

    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public CreateUserOutput execute(CreateUserInput input) {
        if (userGateway.existsByEmail(input.email())) {
            throw new EmailAlreadyExistsException(input.email());
        }

        User user = User.create(input.username(), input.email(), input.phoneNumber());
        User savedUser = userGateway.save(user);

        return new CreateUserOutput(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.isActive(),
                savedUser.getCreatedAt()
        );
    }
}
