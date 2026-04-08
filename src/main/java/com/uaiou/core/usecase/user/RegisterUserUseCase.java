package com.uaiou.core.usecase.user;

import com.uaiou.core.domain.entity.*;
import com.uaiou.core.domain.exception.EmailAlreadyExistsException;
import com.uaiou.core.domain.gateway.DeliveryPersonGateway;
import com.uaiou.core.domain.gateway.EstablishmentGateway;
import com.uaiou.core.domain.gateway.UserGateway;

/**
 * Use Case: Register a new User with a specific type.
 *
 * - If type = DELIVERY_PERSON → creates User + DeliveryPerson (1:1)
 * - If type = ESTABLISHMENT  → creates User + Establishment (1:N owner)
 *
 * Business rules:
 * - Email must be unique
 * - DeliveryPerson requires fiscalCode and cnhCode
 * - Establishment requires name and fiscalCode
 */
public class RegisterUserUseCase {

    private final UserGateway userGateway;
    private final DeliveryPersonGateway deliveryPersonGateway;
    private final EstablishmentGateway establishmentGateway;

    public RegisterUserUseCase(UserGateway userGateway,
                                DeliveryPersonGateway deliveryPersonGateway,
                                EstablishmentGateway establishmentGateway) {
        this.userGateway = userGateway;
        this.deliveryPersonGateway = deliveryPersonGateway;
        this.establishmentGateway = establishmentGateway;
    }

    public RegisterUserOutput execute(RegisterUserInput input) {
        // 1) Validate email uniqueness
        if (userGateway.existsByEmail(input.email())) {
            throw new EmailAlreadyExistsException(input.email());
        }

        // 2) Create and persist User
        User user = User.create(input.username(), input.email(), input.passwordHash(), input.phoneNumber());
        User savedUser = userGateway.save(user);

        // 3) Branch by type
        return switch (input.type()) {
            case DELIVERY_PERSON -> registerDeliveryPerson(savedUser, input);
            case ESTABLISHMENT -> registerEstablishment(savedUser, input);
        };
    }

    private RegisterUserOutput registerDeliveryPerson(User user, RegisterUserInput input) {
        DeliveryPerson deliveryPerson = DeliveryPerson.create(
                input.fiscalCode(),
                input.cnhCode(),
                input.cnhDocument(),
                user
        );
        DeliveryPerson savedDp = deliveryPersonGateway.save(deliveryPerson);

        return new RegisterUserOutput(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isActive(),
                user.getCreatedAt(),
                UserType.DELIVERY_PERSON,
                savedDp.getId(),
                null,
                null
        );
    }

    private RegisterUserOutput registerEstablishment(User user, RegisterUserInput input) {
        Establishment establishment = Establishment.create(
                input.establishmentName(),
                input.establishmentFiscalCode(),
                user
        );
        Establishment savedEst = establishmentGateway.save(establishment);

        return new RegisterUserOutput(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isActive(),
                user.getCreatedAt(),
                UserType.ESTABLISHMENT,
                null,
                savedEst.getId(),
                savedEst.getName()
        );
    }
}
