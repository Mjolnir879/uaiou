package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.Evaluation;
import com.uaiou.core.domain.gateway.EvaluationGateway;

import java.util.Optional;
import java.util.UUID;

public class FindEvaluationByIdUseCase {

    private final EvaluationGateway evaluationGateway;

    public FindEvaluationByIdUseCase(EvaluationGateway evaluationGateway) {
        this.evaluationGateway = evaluationGateway;
    }

    public Optional<Evaluation> execute(UUID id) {
        return evaluationGateway.findById(id);
    }
}
