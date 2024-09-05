package com.gustavolr.model;

public enum RoleName {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    RoleName(final String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}