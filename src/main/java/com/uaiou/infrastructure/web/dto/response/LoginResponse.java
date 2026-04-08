package com.uaiou.infrastructure.web.dto.response;

public record LoginResponse(
        String accessToken,
        long expiresIn
) {
}
