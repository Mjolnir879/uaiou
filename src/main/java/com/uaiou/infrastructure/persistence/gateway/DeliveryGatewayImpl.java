package com.uaiou.infrastructure.persistence.gateway;

import com.uaiou.core.domain.entity.Delivery;
import com.uaiou.core.domain.entity.DeliveryStatusEnum;
import com.uaiou.core.domain.gateway.DeliveryGateway;
import com.uaiou.infrastructure.persistence.entity.DeliveryEntity;
import com.uaiou.infrastructure.persistence.mapper.DeliveryPersistenceMapper;
import com.uaiou.infrastructure.persistence.repository.DeliveryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DeliveryGatewayImpl implements DeliveryGateway {

    private final DeliveryJpaRepository deliveryJpaRepository;
    private final DeliveryPersistenceMapper mapper;

    public DeliveryGatewayImpl(DeliveryJpaRepository deliveryJpaRepository, DeliveryPersistenceMapper mapper) {
        this.deliveryJpaRepository = deliveryJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Delivery save(Delivery delivery) {
        if (delivery.getNumber() == null) {
            Integer lastNumber = deliveryJpaRepository.findTopByOrderByNumberDesc()
                    .map(DeliveryEntity::getNumber)
                    .orElse(0);
            delivery.setNumber(lastNumber + 1);
        }

        DeliveryEntity entity = mapper.toEntity(delivery);
        DeliveryEntity savedEntity = deliveryJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Delivery> findById(UUID id) {
        return deliveryJpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Delivery> findAll(UUID deliveryPersonId, UUID establishmentId, DeliveryStatusEnum status) {
        if (deliveryPersonId != null && establishmentId != null && status != null) {
            return deliveryJpaRepository.findByDeliveryPersonIdAndEstablishmentIdAndStatus(deliveryPersonId, establishmentId, status).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (deliveryPersonId != null && establishmentId != null) {
            return deliveryJpaRepository.findByDeliveryPersonId(deliveryPersonId).stream() // No combined findBy for deliveryPersonId and establishmentId
                    .filter(d -> d.getEstablishment().getId().equals(establishmentId))
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (deliveryPersonId != null && status != null) {
            return deliveryJpaRepository.findByDeliveryPersonId(deliveryPersonId).stream()
                    .filter(d -> d.getStatus().equals(status))
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (establishmentId != null && status != null) {
            return deliveryJpaRepository.findByEstablishmentId(establishmentId).stream()
                    .filter(d -> d.getStatus().equals(status))
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (deliveryPersonId != null) {
            return deliveryJpaRepository.findByDeliveryPersonId(deliveryPersonId).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (establishmentId != null) {
            return deliveryJpaRepository.findByEstablishmentId(establishmentId).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else if (status != null) {
            return deliveryJpaRepository.findByStatus(status).stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        } else {
            return deliveryJpaRepository.findAll().stream()
                    .map(mapper::toDomain)
                    .collect(Collectors.toList());
        }
    }
}
