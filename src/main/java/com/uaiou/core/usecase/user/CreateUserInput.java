package com.uaiou.core.usecase.user;

public record CreateUserInput(
        String username,
        String email,
        String phoneNumber
) {
}
