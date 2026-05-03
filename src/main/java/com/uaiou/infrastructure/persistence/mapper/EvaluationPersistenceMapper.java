package com.uaiou.infrastructure.persistence.mapper;

import com.uaiou.core.domain.entity.Evaluation;
import com.uaiou.infrastructure.persistence.entity.EvaluationEntity;
import org.springframework.stereotype.Component;

@Component
public class EvaluationPersistenceMapper {

    private final EstablishmentPersistenceMapper establishmentMapper;
    private final DeliveryPersonPersistenceMapper deliveryPersonMapper;

    public EvaluationPersistenceMapper(EstablishmentPersistenceMapper establishmentMapper, DeliveryPersonPersistenceMapper deliveryPersonMapper) {
        this.establishmentMapper = establishmentMapper;
        this.deliveryPersonMapper = deliveryPersonMapper;
    }

    public Evaluation toDomain(EvaluationEntity entity) {
        if (entity == null) {
            return null;
        }
        return Evaluation.reconstitute(
                entity.getId(),
                entity.getRating(),
                entity.getNote(),
                entity.getCreatedAt(),
                establishmentMapper.toDomain(entity.getEstablishment()),
                deliveryPersonMapper.toDomain(entity.getDeliveryPerson()),
                entity.getType()
        );
    }

    public EvaluationEntity toEntity(Evaluation domain) {
        if (domain == null) {
            return null;
        }
        return EvaluationEntity.builder()
                .id(domain.getId())
                .rating(domain.getRating())
                .note(domain.getNote())
                .createdAt(domain.getCreatedAt())
                .establishment(establishmentMapper.toEntity(domain.getEstablishment()))
                .deliveryPerson(deliveryPersonMapper.toEntity(domain.getDeliveryPerson()))
                .type(domain.getType())
                .build();
    }
}
