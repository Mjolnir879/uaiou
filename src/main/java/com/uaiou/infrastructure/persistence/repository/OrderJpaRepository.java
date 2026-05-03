package com.uaiou.infrastructure.persistence.repository;

import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
        Optional<OrderEntity> findTopByOrderByNumberDesc();

    List<OrderEntity> findByEstablishmentId(UUID establishmentId);

        List<OrderEntity> findByEstablishmentIdAndDelivered(UUID establishmentId, boolean delivered);

    List<OrderEntity> findByDelivered(boolean delivered);

        @Query("""
                        select o from OrderEntity o
                        where o.delivery is not null
                            and o.delivery.status = :status
                        """)
        List<OrderEntity> findByDeliveryStatus(DeliveryStatusEnum status);

        @Query("""
                        select o from OrderEntity o
                        where o.establishment.id = :establishmentId
                            and o.delivery is not null
                            and o.delivery.status = :status
                        """)
        List<OrderEntity> findByEstablishmentIdAndDeliveryStatus(UUID establishmentId, DeliveryStatusEnum status);

        @Query("""
                        select o from OrderEntity o
                        where o.delivered = :delivered
                            and o.delivery is not null
                            and o.delivery.status = :status
                        """)
        List<OrderEntity> findByDeliveredAndDeliveryStatus(boolean delivered, DeliveryStatusEnum status);

        @Query("""
                        select o from OrderEntity o
                        where o.establishment.id = :establishmentId
                            and o.delivered = :delivered
                            and o.delivery is not null
                            and o.delivery.status = :status
                        """)
        List<OrderEntity> findByEstablishmentIdAndDeliveredAndDeliveryStatus(UUID establishmentId, boolean delivered, DeliveryStatusEnum status);
}
