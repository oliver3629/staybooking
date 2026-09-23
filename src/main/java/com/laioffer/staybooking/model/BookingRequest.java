package com.laioffer.staybooking.model;

import java.time.LocalDate;

public record BookingRequest(
        long listingId,
        LocalDate checkInDate,
        LocalDate checkOutDate
) {
}
