package com.uaiou.infrastructure.persistence.mapper;

import com.uaiou.core.domain.entity.Address;
import com.uaiou.infrastructure.persistence.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AddressPersistenceMapper {

    default Address toDomain(AddressEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Address(
                entity.getId(),
            entity.getStreet(),
            entity.getComplement() != null ? entity.getComplement() : entity.getNumber(),
                entity.getCity(),
                entity.getState(),
            entity.getZipCode(),
            entity.getNeighborhood(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }

    default AddressEntity toEntity(Address domain) {
        if (domain == null) {
            return null;
        }

        AddressEntity entity = new AddressEntity();
        entity.setId(domain.getId());
        entity.setStreet(domain.getLine1());
        entity.setNumber(null);
        entity.setComplement(domain.getLine2());
        entity.setCity(domain.getCity());
        entity.setState(domain.getState());
        entity.setZipCode(domain.getPostalCode());
        entity.setNeighborhood(domain.getNeighborhood());
        entity.setLatitude(domain.getLatitude());
        entity.setLongitude(domain.getLongitude());
        return entity;
    }
}
