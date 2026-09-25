package com.laioffer.staybooking.model;

public record RegisterRequest(
        String username,
        String password,
        UserRole role
) {
}
