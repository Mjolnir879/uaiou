package com.uaiou.infrastructure.web.dto.request;

import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import jakarta.validation.constraints.NotNull;

public record UpdateDeliveryStatusRequest(
        @NotNull(message = "Delivery status is required")
        DeliveryStatusEnum status
) {
}
