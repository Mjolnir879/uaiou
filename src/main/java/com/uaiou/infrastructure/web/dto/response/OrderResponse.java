package com.uaiou.infrastructure.web.dto.response;

import com.uaiou.core.domain.entity.OrderSpecificsEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        Integer number,
        String name,
        OrderSpecificsEnum specifics,
        UUID addressId,
        UUID establishmentId,
        UUID deliveryId,
        LocalDateTime createdAt,
        LocalDateTime deliveredAt,
        boolean delivered,
        String orderTypeCode
) {
}
