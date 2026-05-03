package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.EvaluationTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateEvaluationOutput(
        UUID id,
        BigDecimal rating,
        String note,
        LocalDateTime createdAt,
        UUID establishmentId,
        UUID deliveryPersonId,
        EvaluationTypeEnum type
) {
}
