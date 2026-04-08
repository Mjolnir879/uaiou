package com.uaiou.infrastructure.web.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String phoneNumber,
        boolean active,
        LocalDateTime createdAt
) {
}
