package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Evaluation;
import com.uaiou.core.domain.entity.EvaluationTypeEnum;
import com.uaiou.core.domain.gateway.EvaluationGateway;

import java.util.List;
import java.util.UUID;

public class ListEvaluationsUseCase {

    private final EvaluationGateway evaluationGateway;

    public ListEvaluationsUseCase(EvaluationGateway evaluationGateway) {
        this.evaluationGateway = evaluationGateway;
    }

    public List<Evaluation> execute(EvaluationTypeEnum type, UUID establishmentId, UUID deliveryPersonId) {
        return evaluationGateway.findAll(type, establishmentId, deliveryPersonId);
    }
}
