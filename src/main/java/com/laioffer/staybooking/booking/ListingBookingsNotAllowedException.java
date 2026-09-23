package com.laioffer.staybooking.booking;

public class ListingBookingsNotAllowedException extends RuntimeException {

    public ListingBookingsNotAllowedException(long hostId, long listingId) {

        super("Host " + hostId + " not allowed to get bookings of listing " + listingId);
    }
}

