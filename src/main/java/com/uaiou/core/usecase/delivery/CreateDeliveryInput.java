package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.domain.entity.OrderSpecificsEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateDeliveryInput(
        UUID deliveryPersonId,
        UUID establishmentId,
        List<UUID> orderIds,
        UUID addressId,
        BigDecimal value
) {
}
