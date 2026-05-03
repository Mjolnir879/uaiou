package com.uaiou.infrastructure.persistence.repository;

import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.infrastructure.persistence.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity, UUID> {
    Optional<DeliveryEntity> findTopByOrderByNumberDesc();

    List<DeliveryEntity> findByDeliveryPersonIdAndEstablishmentIdAndStatus(UUID deliveryPersonId, UUID establishmentId, DeliveryStatusEnum status);
    List<DeliveryEntity> findByDeliveryPersonId(UUID deliveryPersonId);
    List<DeliveryEntity> findByEstablishmentId(UUID establishmentId);
    List<DeliveryEntity> findByStatus(DeliveryStatusEnum status);
}
