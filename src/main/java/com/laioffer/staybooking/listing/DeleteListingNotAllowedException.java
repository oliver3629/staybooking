package com.laioffer.staybooking.listing;

public class DeleteListingNotAllowedException extends RuntimeException {

    public DeleteListingNotAllowedException(String message) {
        super(message);
    }
}
