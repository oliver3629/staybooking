package com.laioffer.staybooking.model;

import java.util.List;

public record ListingDto(
        Long id,
        String name,
        String address,
        String description,
        Integer guestNumber,
        List<String> images,
        GeoPoint location,
        UserDto host
) {
    public ListingDto(ListingEntity entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getDescription(),
                entity.getGuestNumber(),
                entity.getImageUrls(),
                new GeoPoint(entity.getLocation().getCoordinate().y, entity.getLocation().getCoordinate().x),
                new UserDto(entity.getHost())
        );
    }
}
