package com.uaiou.core.usecase.user;

import com.uaiou.core.domain.entity.UserType;

public record RegisterUserInput(
        // User fields
        String username,
        String email,
        String passwordHash,
        String phoneNumber,

        // Type discriminator
        UserType type,

        // DeliveryPerson-specific fields (nullable when type = ESTABLISHMENT)
        String fiscalCode,
        String cnhCode,
        String cnhDocument,

        // Establishment-specific fields (nullable when type = DELIVERY_PERSON)
        String establishmentName,
        String establishmentFiscalCode
) {
}
