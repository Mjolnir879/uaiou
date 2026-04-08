package com.uaiou.core.usecase.user;

import com.uaiou.core.domain.entity.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterUserOutput(
        UUID userId,
        String username,
        String email,
        String phoneNumber,
        boolean active,
        LocalDateTime createdAt,
        UserType type,

        // Populated when type = DELIVERY_PERSON
        UUID deliveryPersonId,

        // Populated when type = ESTABLISHMENT
        UUID establishmentId,
        String establishmentName
) {
}
