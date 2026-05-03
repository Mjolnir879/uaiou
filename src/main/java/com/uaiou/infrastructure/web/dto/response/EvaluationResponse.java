package com.uaiou.infrastructure.web.dto.response;

import com.uaiou.core.domain.entity.EvaluationTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record EvaluationResponse(
        UUID id,
        BigDecimal rating,
        String note,
        LocalDateTime createdAt,
        UUID establishmentId,
        UUID deliveryPersonId,
        EvaluationTypeEnum type
) {
}
