package com.uaiou.core.domain.gateway;

import com.uaiou.core.domain.entity.Evaluation;
import com.uaiou.core.domain.entity.EvaluationTypeEnum;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EvaluationGateway {
    Evaluation save(Evaluation evaluation);
    Optional<Evaluation> findById(UUID id);
    List<Evaluation> findAll(EvaluationTypeEnum type, UUID establishmentId, UUID deliveryPersonId);
    BigDecimal getAverageRatingForEstablishment(UUID establishmentId);
    BigDecimal getAverageRatingForDeliveryPerson(UUID deliveryPersonId);
}
