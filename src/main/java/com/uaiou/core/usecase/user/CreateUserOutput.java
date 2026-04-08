package com.uaiou.core.usecase.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateUserOutput(
        UUID id,
        String username,
        String email,
        String phoneNumber,
        boolean active,
        LocalDateTime createdAt
) {
}
