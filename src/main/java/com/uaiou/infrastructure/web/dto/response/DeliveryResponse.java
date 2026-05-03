package com.uaiou.infrastructure.web.dto.response;

import com.uaiou.core.domain.entity.DeliveryStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DeliveryResponse(
        UUID id,
        Integer number,
        UUID deliveryPersonId,
        UUID establishmentId,
        List<UUID> orderIds,
        UUID addressId,
        LocalDateTime createdAt,
        LocalDateTime deliveredAt,
        boolean isFinished,
        DeliveryStatusEnum status,
        BigDecimal value,
        boolean paid
) {
}
