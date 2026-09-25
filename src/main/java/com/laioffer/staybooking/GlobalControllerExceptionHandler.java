package com.laioffer.staybooking;

import com.laioffer.staybooking.authentication.UserAlreadyExistException;
import com.laioffer.staybooking.booking.DeleteBookingNotAllowedException;
import com.laioffer.staybooking.booking.InvalidBookingException;
import com.laioffer.staybooking.booking.ListingBookingsNotAllowedException;
import com.laioffer.staybooking.listing.DeleteListingNotAllowedException;
import com.laioffer.staybooking.listing.InvalidListingSearchException;
import com.laioffer.staybooking.location.InvalidAddressException;
import com.laioffer.staybooking.model.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @ExceptionHandler(DeleteListingNotAllowedException.class)
    public final ResponseEntity<ErrorResponse> handleException(DeleteListingNotAllowedException e) {
        return new ResponseEntity<>(new ErrorResponse(
                e.getMessage(),
                "delete_listing_not_allowed"),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidListingSearchException.class)
    public final ResponseEntity<ErrorResponse> handleException(InvalidListingSearchException ex) {
        return new ResponseEntity<>(new ErrorResponse(
                ex.getMessage(),
                "invalid_search_request"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<ErrorResponse> handleException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(new ErrorResponse(
                "Required request parameter is missing",
                "missing_request_parameter"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAddressException.class)
    public final ResponseEntity<ErrorResponse> handleException(InvalidAddressException ex) {
        return  new ResponseEntity<>(new ErrorResponse(
                ex.getMessage(),
                "invalid_address_error"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<ErrorResponse> handleException(UserAlreadyExistException ex) {
        return new ResponseEntity<>(new ErrorResponse(
                ex.getMessage(),
                "user_already_exist"),
                HttpStatus.CONFLICT);
    }
}
