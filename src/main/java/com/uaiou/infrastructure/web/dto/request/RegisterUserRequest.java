package com.uaiou.infrastructure.web.dto.request;

import com.uaiou.core.domain.entity.UserType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

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

        @AssertTrue(message = "fiscalCode and cnhCode are required when type is DELIVERY_PERSON")
        public boolean isDeliveryPersonFieldsValid() {
                if (type != UserType.DELIVERY_PERSON) {
                        return true;
                }
                return fiscalCode != null && !fiscalCode.isBlank()
                                && cnhCode != null && !cnhCode.isBlank();
        }

        @AssertTrue(message = "establishmentName and establishmentFiscalCode are required when type is ESTABLISHMENT")
        public boolean isEstablishmentFieldsValid() {
                if (type != UserType.ESTABLISHMENT) {
                        return true;
                }
                return establishmentName != null && !establishmentName.isBlank()
                                && establishmentFiscalCode != null && !establishmentFiscalCode.isBlank();
        }
}
