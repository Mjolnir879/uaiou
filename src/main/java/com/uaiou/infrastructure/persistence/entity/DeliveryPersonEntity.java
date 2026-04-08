package com.uaiou.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "delivery_persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPersonEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "fiscal_code", nullable = false, unique = true, length = 20)
    private String fiscalCode;

    @Column(name = "cnh_code", nullable = false, unique = true, length = 20)
    private String cnhCode;

    @Column(name = "cnh_document", length = 255)
    private String cnhDocument;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;
}
