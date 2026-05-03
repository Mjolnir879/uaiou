package com.uaiou.infrastructure.persistence.repository;

import com.uaiou.infrastructure.persistence.entity.OrderTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTypeJpaRepository extends JpaRepository<OrderTypeEntity, String> {
}
