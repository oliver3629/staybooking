package com.laioffer.staybooking;

import com.laioffer.staybooking.booking.DeleteBookingNotAllowedException;
import com.laioffer.staybooking.booking.InvalidBookingException;
import com.laioffer.staybooking.booking.ListingBookingsNotAllowedException;
import com.laioffer.staybooking.model.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleException(EntityNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(
                "Resource not found",
                "resource_not_found"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ListingBookingsNotAllowedException.class)
    public final ResponseEntity<ErrorResponse> handleException(ListingBookingsNotAllowedException e) {
        return new ResponseEntity<>(new ErrorResponse(
                "Cannot get listing bookings",
                "listing_bookings_not_allowed"),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DeleteBookingNotAllowedException.class)
    public final ResponseEntity<ErrorResponse> handleException(DeleteBookingNotAllowedException e) {
        return new ResponseEntity<>(new ErrorResponse(
                "Cannot delete booking",
                "delete_booking_not_allowed"),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidBookingException.class)
    public final ResponseEntity<ErrorResponse> handleException(InvalidBookingException e) {
        return new ResponseEntity<>(new ErrorResponse(
                e.getMessage(),
                "invalid_booking_request"),
                HttpStatus.BAD_REQUEST);
    }
}
