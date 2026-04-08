package com.uaiou.core.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private String phoneNumber;
    private boolean active;
    private LocalDateTime createdAt;

    private User() {
    }

    private User(UUID id, String username, String email, String passwordHash, String phoneNumber, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.createdAt = createdAt;
    }

    /**
     * Factory method to create a new User with auto-generated UUID and timestamp.
     */
    public static User create(String username, String email, String passwordHash, String phoneNumber) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash must not be blank");
        }

        return new User(
                UUID.randomUUID(),
                username,
                email,
                passwordHash,
                phoneNumber,
                true,
                LocalDateTime.now()
        );
    }

    /**
     * Reconstitution factory — used to rebuild a domain entity from persistence.
     */
    public static User reconstitute(UUID id, String username, String email, String passwordHash, String phoneNumber, boolean active, LocalDateTime createdAt) {
        return new User(id, username, email, passwordHash, phoneNumber, active, createdAt);
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    // --- Getters (no setters — domain integrity) ---

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
