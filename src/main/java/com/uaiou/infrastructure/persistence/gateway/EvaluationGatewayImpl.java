package com.uaiou.infrastructure.persistence.gateway;

import com.uaiou.core.domain.entity.Evaluation;
import com.uaiou.core.domain.entity.EvaluationTypeEnum;
import com.uaiou.core.domain.gateway.EvaluationGateway;
import com.uaiou.infrastructure.persistence.entity.EvaluationEntity;
import com.uaiou.infrastructure.persistence.mapper.EvaluationPersistenceMapper;
import com.uaiou.infrastructure.persistence.repository.EvaluationJpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EvaluationGatewayImpl implements EvaluationGateway {

    private final EvaluationJpaRepository evaluationJpaRepository;
    private final EvaluationPersistenceMapper mapper;

    public EvaluationGatewayImpl(EvaluationJpaRepository evaluationJpaRepository, EvaluationPersistenceMapper mapper) {
        this.evaluationJpaRepository = evaluationJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Evaluation save(Evaluation evaluation) {
        EvaluationEntity entity = mapper.toEntity(evaluation);
        EvaluationEntity savedEntity = evaluationJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Evaluation> findById(UUID id) {
        return evaluationJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Evaluation> findAll(EvaluationTypeEnum type, UUID establishmentId, UUID deliveryPersonId) {
        if (type != null && establishmentId != null && deliveryPersonId != null) {
            return evaluationJpaRepository.findByTypeAndEstablishmentIdAndDeliveryPersonId(type, establishmentId, deliveryPersonId).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (establishmentId != null) {
            return evaluationJpaRepository.findByEstablishmentId(establishmentId).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (deliveryPersonId != null) {
            return evaluationJpaRepository.findByDeliveryPersonId(deliveryPersonId).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (type != null) {
            return evaluationJpaRepository.findByType(type).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else {
            return evaluationJpaRepository.findAll().stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public BigDecimal getAverageRatingForEstablishment(UUID establishmentId) {
        return evaluationJpaRepository.findAverageRatingByEstablishmentId(establishmentId);
    }

    @Override
    public BigDecimal getAverageRatingForDeliveryPerson(UUID deliveryPersonId) {
        return evaluationJpaRepository.findAverageRatingByDeliveryPersonId(deliveryPersonId);
    }
}
