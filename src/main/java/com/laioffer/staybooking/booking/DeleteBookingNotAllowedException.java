package com.laioffer.staybooking.booking;

public class DeleteBookingNotAllowedException extends RuntimeException {

    public DeleteBookingNotAllowedException(long guestId, long bookingId) {

        super("Guest " + guestId + " not allow to delete the booking " + bookingId);
    }
}
