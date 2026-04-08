package com.uaiou.infrastructure.persistence.mapper;

import com.uaiou.core.domain.entity.Establishment;
import com.uaiou.infrastructure.persistence.entity.EstablishmentEntity;
import com.uaiou.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.Collections;

@Mapper
public interface EstablishmentPersistenceMapper {

    default EstablishmentEntity toEntity(Establishment est) {
        if (est == null) return null;

        EstablishmentEntity entity = new EstablishmentEntity();
        entity.setId(est.getId());
        entity.setName(est.getName());
        entity.setProfileImage(est.getProfileImage());
        entity.setFiscalCode(est.getFiscalCode());
        entity.setRating(est.getRating());

        if (est.getOwner() != null) {
            UserEntity ue = new UserEntity();
            ue.setId(est.getOwner().getId());
            ue.setUsername(est.getOwner().getUsername());
            ue.setEmail(est.getOwner().getEmail());
            ue.setPasswordHash(est.getOwner().getPasswordHash());
            ue.setPhoneNumber(est.getOwner().getPhoneNumber());
            ue.setActive(est.getOwner().isActive());
            ue.setCreatedAt(est.getOwner().getCreatedAt());
            entity.setOwner(ue);
        }

        return entity;
    }

    default Establishment toDomain(EstablishmentEntity entity) {
        if (entity == null) return null;

        com.uaiou.core.domain.entity.User owner = null;
        if (entity.getOwner() != null) {
            var ue = entity.getOwner();
            owner = com.uaiou.core.domain.entity.User.reconstitute(
                    ue.getId(), ue.getUsername(), ue.getEmail(),
                    ue.getPasswordHash(),
                    ue.getPhoneNumber(), ue.isActive(), ue.getCreatedAt()
            );
        }

        return Establishment.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.getProfileImage(),
                entity.getFiscalCode(),
                entity.getRating(),
                owner,
                Collections.emptyList()
        );
    }
}
