package com.uaiou.core.domain.entity;

import java.util.UUID;

public class DeliveryPerson {

    private UUID id;
    private Double rating;
    private String fiscalCode;
    private String cnhCode;
    private String cnhDocument;
    private User user;
    private Address address;

    private DeliveryPerson() {
    }

    private DeliveryPerson(UUID id, Double rating, String fiscalCode, String cnhCode, String cnhDocument, User user, Address address) {
        this.id = id;
        this.rating = rating;
        this.fiscalCode = fiscalCode;
        this.cnhCode = cnhCode;
        this.cnhDocument = cnhDocument;
        this.user = user;
        this.address = address;
    }

    public static DeliveryPerson create(String fiscalCode, String cnhCode, String cnhDocument, User user) {
        if (fiscalCode == null || fiscalCode.isBlank()) {
            throw new IllegalArgumentException("Fiscal code must not be blank");
        }
        if (cnhCode == null || cnhCode.isBlank()) {
            throw new IllegalArgumentException("CNH code must not be blank");
        }
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        return new DeliveryPerson(
                UUID.randomUUID(),
                0.0,
                fiscalCode,
                cnhCode,
                cnhDocument,
                user,
                null
        );
    }

    public static DeliveryPerson reconstitute(UUID id, Double rating, String fiscalCode, String cnhCode, String cnhDocument, User user, Address address) {
        return new DeliveryPerson(id, rating, fiscalCode, cnhCode, cnhDocument, user, address);
    }

    public void updateRating(Double newRating) {
        if (newRating < 0 || newRating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        this.rating = newRating;
    }

    public void assignAddress(Address address) {
        this.address = address;
    }

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public Double getRating() {
        return rating;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getCnhCode() {
        return cnhCode;
    }

    public String getCnhDocument() {
        return cnhDocument;
    }

    public User getUser() {
        return user;
    }

    public Address getAddress() {
        return address;
    }
}
