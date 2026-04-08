package com.uaiou.infrastructure.persistence.repository;

import com.uaiou.infrastructure.persistence.entity.DeliveryPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryPersonJpaRepository extends JpaRepository<DeliveryPersonEntity, UUID> {
}
