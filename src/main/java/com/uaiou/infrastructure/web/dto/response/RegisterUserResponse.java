package com.uaiou.infrastructure.web.dto.response;

import com.uaiou.core.domain.entity.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterUserResponse(
        UUID userId,
        String username,
        String email,
        String phoneNumber,
        boolean active,
        LocalDateTime createdAt,
        UserType type,
        UUID deliveryPersonId,
        UUID establishmentId,
        String establishmentName
) {
}
