package com.uaiou.infrastructure.web.dto.request;

import com.uaiou.core.domain.entity.EvaluationTypeEnum;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateEvaluationRequest(
        @NotNull(message = "Rating is required")
        @DecimalMin(value = "1.0", message = "Rating must be at least 1.0")
        @DecimalMax(value = "5.0", message = "Rating must be at most 5.0")
        BigDecimal rating,
        @NotBlank(message = "Note is required")
        String note,
        UUID establishmentId,
        UUID deliveryPersonId,
        @NotNull(message = "Evaluation type is required")
        EvaluationTypeEnum type
) {
}
