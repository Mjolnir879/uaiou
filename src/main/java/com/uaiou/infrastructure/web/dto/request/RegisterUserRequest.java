package com.uaiou.infrastructure.web.dto.request;

import com.uaiou.core.domain.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        String phoneNumber,

        @NotNull(message = "Type is required (DELIVERY_PERSON or ESTABLISHMENT)")
        UserType type,

        // DeliveryPerson fields
        String fiscalCode,
        String cnhCode,
        String cnhDocument,

        // Establishment fields
        String establishmentName,
        String establishmentFiscalCode
) {
}
