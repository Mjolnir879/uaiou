package com.uaiou.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateDeliveryRequest(
        @NotNull(message = "Delivery Person ID is required")
        UUID deliveryPersonId,
        @NotNull(message = "Establishment ID is required")
        UUID establishmentId,
        @NotNull(message = "At least one order ID is required")
        @Size(min = 1, message = "At least one order ID is required")
        List<UUID> orderIds,
        @NotNull(message = "Delivery Address ID is required")
        UUID addressId,
        @NotNull(message = "Delivery value is required")
        BigDecimal value
) {
}
