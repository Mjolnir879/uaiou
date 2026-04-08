package com.uaiou.core.domain.entity;

import java.util.UUID;

/**
 * Address Value Object.
 * Immutable by design — all fields set at construction time.
 */
public class Address {

    private final UUID id;
    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String zipCode;
    private final Double latitude;
    private final Double longitude;

    public Address(UUID id, String street, String number, String complement,
                   String neighborhood, String city, String state, String zipCode,
                   Double latitude, Double longitude) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Address create(String street, String number, String complement,
                                  String neighborhood, String city, String state, String zipCode,
                                  Double latitude, Double longitude) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street must not be blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City must not be blank");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State must not be blank");
        }

        return new Address(UUID.randomUUID(), street, number, complement, neighborhood, city, state, zipCode, latitude, longitude);
    }

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
