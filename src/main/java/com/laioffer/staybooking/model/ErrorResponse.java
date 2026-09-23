package com.laioffer.staybooking.model;

public record ErrorResponse(
        String message,
        String error
) {
}
