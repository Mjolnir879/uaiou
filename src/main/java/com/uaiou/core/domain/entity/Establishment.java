package com.uaiou.core.domain.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Establishment {

    private UUID id;
    private String name;
    private String profileImage;
    private String fiscalCode;
    private Double rating;
    private User owner;
    private List<Address> addresses;

    private Establishment() {
    }

    private Establishment(UUID id, String name, String profileImage, String fiscalCode, Double rating, User owner, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.profileImage = profileImage;
        this.fiscalCode = fiscalCode;
        this.rating = rating;
        this.owner = owner;
        this.addresses = addresses != null ? new ArrayList<>(addresses) : new ArrayList<>();
    }

    public static Establishment create(String name, String fiscalCode, User owner) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Establishment name must not be blank");
        }
        if (fiscalCode == null || fiscalCode.isBlank()) {
            throw new IllegalArgumentException("Fiscal code must not be blank");
        }
        if (owner == null) {
            throw new IllegalArgumentException("Owner must not be null");
        }

        return new Establishment(
                UUID.randomUUID(),
                name,
                null,
                fiscalCode,
                0.0,
                owner,
                new ArrayList<>()
        );
    }

    public static Establishment reconstitute(UUID id, String name, String profileImage, String fiscalCode, Double rating, User owner, List<Address> addresses) {
        return new Establishment(id, name, profileImage, fiscalCode, rating, owner, addresses);
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void updateRating(Double newRating) {
        if (newRating < 0 || newRating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        this.rating = newRating;
    }

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public Double getRating() {
        return rating;
    }

    public User getOwner() {
        return owner;
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }
}
