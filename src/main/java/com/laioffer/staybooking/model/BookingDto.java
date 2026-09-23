package com.laioffer.staybooking.model;

import java.time.LocalDate;

public record BookingDto(
        Long id,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        UserDto guest,
        ListingDto listing
) {
    public BookingDto(BookingEntity entity) {
        this(
                entity.getId(),
                entity.getCheckInDate(),
                entity.getCheckOutDate(),
                new UserDto(entity.getGuest()),
                new ListingDto(entity.getListing())
        );
    }
}
