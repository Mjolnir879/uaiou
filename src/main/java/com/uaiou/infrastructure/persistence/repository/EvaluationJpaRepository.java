package com.uaiou.infrastructure.persistence.repository;

import com.uaiou.core.domain.entity.EvaluationTypeEnum;
import com.uaiou.infrastructure.persistence.entity.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface EvaluationJpaRepository extends JpaRepository<EvaluationEntity, UUID> {
    List<EvaluationEntity> findByTypeAndEstablishmentIdAndDeliveryPersonId(EvaluationTypeEnum type, UUID establishmentId, UUID deliveryPersonId);
    List<EvaluationEntity> findByEstablishmentId(UUID establishmentId);
    List<EvaluationEntity> findByDeliveryPersonId(UUID deliveryPersonId);
    List<EvaluationEntity> findByType(EvaluationTypeEnum type);

    @Query("SELECT AVG(e.rating) FROM EvaluationEntity e WHERE e.establishment.id = :establishmentId")
    BigDecimal findAverageRatingByEstablishmentId(UUID establishmentId);

    @Query("SELECT AVG(e.rating) FROM EvaluationEntity e WHERE e.deliveryPerson.id = :deliveryPersonId")
    BigDecimal findAverageRatingByDeliveryPersonId(UUID deliveryPersonId);
}
