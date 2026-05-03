package com.uaiou.core.usecase.delivery;

import com.uaiou.core.domain.entity.DeliveryPerson;
import com.uaiou.core.domain.entity.Establishment;
import com.uaiou.core.domain.entity.Evaluation;
import com.uaiou.core.domain.entity.EvaluationTypeEnum;
import com.uaiou.core.domain.exception.DomainException;
import com.uaiou.core.domain.gateway.DeliveryPersonGateway;
import com.uaiou.core.domain.gateway.EstablishmentGateway;
import com.uaiou.core.domain.gateway.EvaluationGateway;

import java.math.BigDecimal;
import java.util.Optional;

public class CreateEvaluationUseCase {

    private final EvaluationGateway evaluationGateway;
    private final EstablishmentGateway establishmentGateway;
    private final DeliveryPersonGateway deliveryPersonGateway;

    public CreateEvaluationUseCase(EvaluationGateway evaluationGateway, EstablishmentGateway establishmentGateway, DeliveryPersonGateway deliveryPersonGateway) {
        this.evaluationGateway = evaluationGateway;
        this.establishmentGateway = establishmentGateway;
        this.deliveryPersonGateway = deliveryPersonGateway;
    }

    public CreateEvaluationOutput execute(CreateEvaluationInput input) {
        Establishment establishment = null;
        DeliveryPerson deliveryPerson = null;

        if (input.type() == EvaluationTypeEnum.ESTABLISHMENT_EVALUATION) {
            if (input.establishmentId() == null) {
                throw new DomainException("Establishment ID is required for establishment evaluation");
            }
            establishment = establishmentGateway.findById(input.establishmentId())
                    .orElseThrow(() -> new DomainException("Establishment not found"));
        } else if (input.type() == EvaluationTypeEnum.DELIVERY_EVALUATION) {
            if (input.deliveryPersonId() == null) {
                throw new DomainException("Delivery person ID is required for delivery evaluation");
            }
            deliveryPerson = deliveryPersonGateway.findById(input.deliveryPersonId())
                    .orElseThrow(() -> new DomainException("Delivery Person not found"));
        } else {
            throw new DomainException("Evaluation type not supported");
        }

        Evaluation evaluation = Evaluation.create(input.rating(), input.note(), establishment, deliveryPerson, input.type());
        Evaluation savedEvaluation = evaluationGateway.save(evaluation);

        // Update average ratings
        if (establishment != null) {
            BigDecimal averageRating = evaluationGateway.getAverageRatingForEstablishment(establishment.getId());
            establishment.updateRating(averageRating != null ? averageRating.doubleValue() : 0.0);
            establishmentGateway.save(establishment);
        }
        if (deliveryPerson != null) {
            BigDecimal averageRating = evaluationGateway.getAverageRatingForDeliveryPerson(deliveryPerson.getId());
            deliveryPerson.updateRating(averageRating != null ? averageRating.doubleValue() : 0.0);
            deliveryPersonGateway.save(deliveryPerson);
        }

        return new CreateEvaluationOutput(
                savedEvaluation.getId(),
                savedEvaluation.getRating(),
                savedEvaluation.getNote(),
                savedEvaluation.getCreatedAt(),
                Optional.ofNullable(savedEvaluation.getEstablishment()).map(Establishment::getId).orElse(null),
                Optional.ofNullable(savedEvaluation.getDeliveryPerson()).map(DeliveryPerson::getId).orElse(null),
                savedEvaluation.getType()
        );
    }
}
