package com.uaiou.infrastructure.persistence.mapper;

import com.uaiou.core.domain.entity.User;
import com.uaiou.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserPersistenceMapper {

    UserEntity toEntity(User user);

    default User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.reconstitute(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.isActive(),
                entity.getCreatedAt()
        );
    }
}
