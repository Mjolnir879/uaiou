package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.EvaluationTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateEvaluationInput(
        BigDecimal rating,
        String note,
        UUID establishmentId,
        UUID deliveryPersonId,
        EvaluationTypeEnum type
) {
}
