package com.uaiou.infrastructure.persistence.mapper;

import com.uaiou.core.domain.entity.DeliveryPerson;
import com.uaiou.infrastructure.persistence.entity.DeliveryPersonEntity;
import com.uaiou.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DeliveryPersonPersistenceMapper {

    default DeliveryPersonEntity toEntity(DeliveryPerson dp) {
        if (dp == null) return null;

        DeliveryPersonEntity entity = new DeliveryPersonEntity();
        entity.setId(dp.getId());
        entity.setRating(dp.getRating());
        entity.setFiscalCode(dp.getFiscalCode());
        entity.setCnhCode(dp.getCnhCode());
        entity.setCnhDocument(dp.getCnhDocument());

        if (dp.getUser() != null) {
            UserEntity ue = new UserEntity();
            ue.setId(dp.getUser().getId());
            ue.setUsername(dp.getUser().getUsername());
            ue.setEmail(dp.getUser().getEmail());
            ue.setPasswordHash(dp.getUser().getPasswordHash());
            ue.setPhoneNumber(dp.getUser().getPhoneNumber());
            ue.setActive(dp.getUser().isActive());
            ue.setCreatedAt(dp.getUser().getCreatedAt());
            entity.setUser(ue);
        }

        return entity;
    }

    default DeliveryPerson toDomain(DeliveryPersonEntity entity) {
        if (entity == null) return null;

        com.uaiou.core.domain.entity.User user = null;
        if (entity.getUser() != null) {
            var ue = entity.getUser();
            user = com.uaiou.core.domain.entity.User.reconstitute(
                    ue.getId(), ue.getUsername(), ue.getEmail(),
                    ue.getPasswordHash(),
                    ue.getPhoneNumber(), ue.isActive(), ue.getCreatedAt()
            );
        }

        return DeliveryPerson.reconstitute(
                entity.getId(),
                entity.getRating(),
                entity.getFiscalCode(),
                entity.getCnhCode(),
                entity.getCnhDocument(),
                user,
                null
        );
    }
}
