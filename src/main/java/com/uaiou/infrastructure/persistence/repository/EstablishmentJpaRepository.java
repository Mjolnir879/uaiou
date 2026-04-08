package com.uaiou.infrastructure.persistence.repository;

import com.uaiou.infrastructure.persistence.entity.EstablishmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstablishmentJpaRepository extends JpaRepository<EstablishmentEntity, UUID> {
}
