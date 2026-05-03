package com.uaiou.core.domain.entity;

import java.util.UUID;

/**
 * Address Value Object.
 * Immutable by design — all fields set at construction time.
 */
public class Address {

    private final UUID id;
    private final String line1;
    private final String line2;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String neighborhood;
    private final Double latitude;
    private final Double longitude;

    public Address(UUID id, String line1, String line2,
                   String city, String state, String postalCode,
                   String neighborhood,
                   Double latitude, Double longitude) {
        this.id = id;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.neighborhood = neighborhood;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Address create(String line1, String line2,
                                  String city, String state, String postalCode,
                                  String neighborhood,
                                  Double latitude, Double longitude) {
        if (line1 == null || line1.isBlank()) {
            throw new IllegalArgumentException("Line 1 must not be blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City must not be blank");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State must not be blank");
        }

        return new Address(UUID.randomUUID(), line1, line2, city, state, postalCode, neighborhood, latitude, longitude);
    }

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
