package com.uaiou.infrastructure.web.mapper;

import com.uaiou.core.domain.entity.DeliveryPerson;
import com.uaiou.core.domain.entity.Establishment;
import com.uaiou.core.domain.entity.Evaluation;
import com.uaiou.infrastructure.web.dto.request.CreateEvaluationRequest;
import com.uaiou.infrastructure.web.dto.response.EvaluationResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EvaluationDtoMapper {

    public Evaluation toDomain(CreateEvaluationRequest request, Establishment establishment, DeliveryPerson deliveryPerson) {
        return Evaluation.create(
                request.rating(),
                request.note(),
                establishment,
                deliveryPerson,
                request.type()
        );
    }

    public EvaluationResponse toResponse(Evaluation evaluation) {
        return new EvaluationResponse(
                evaluation.getId(),
                evaluation.getRating(),
                evaluation.getNote(),
                evaluation.getCreatedAt(),
                Optional.ofNullable(evaluation.getEstablishment()).map(Establishment::getId).orElse(null),
                Optional.ofNullable(evaluation.getDeliveryPerson()).map(DeliveryPerson::getId).orElse(null),
                evaluation.getType()
        );
    }
}
