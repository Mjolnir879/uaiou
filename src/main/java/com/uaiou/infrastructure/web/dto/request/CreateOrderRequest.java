package com.uaiou.infrastructure.web.dto.request;

import com.uaiou.core.domain.entity.OrderSpecificsEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateOrderRequest(
        @NotBlank(message = "Order name is required")
        String name,
        @NotNull(message = "Order specifics is required")
        OrderSpecificsEnum specifics,
        @NotNull(message = "Address ID is required")
        UUID addressId,
        @NotNull(message = "Establishment ID is required")
        UUID establishmentId,
        @NotBlank(message = "Order type code is required")
        String orderTypeCode
) {
}
