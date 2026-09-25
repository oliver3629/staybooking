package com.laioffer.staybooking.listing;

import com.laioffer.staybooking.booking.BookingService;
import com.laioffer.staybooking.model.BookingDto;
import com.laioffer.staybooking.model.ListingDto;
import com.laioffer.staybooking.model.UserEntity;
import com.laioffer.staybooking.model.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {

    private final BookingService bookingService;
    private final ListingService listingService;

    private final UserEntity user = new UserEntity(1L, "rich_the_landlord", "YT61cW", UserRole.ROLE_HOST);

    public ListingController(BookingService bookingService, ListingService listingService) {
        this.bookingService = bookingService;
        this.listingService = listingService;
    }

    @GetMapping
    public List<ListingDto> getListings() {
        return listingService.getListings(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createListing(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("description") String description,
            @RequestParam("guest_number") int guestNumber,
            @RequestParam("images") List<MultipartFile> images
    ) {
        listingService.createListing(user.getId(), name, address, description, guestNumber, images);
    }

    @DeleteMapping("/{listingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteListing(@PathVariable Long listingId) {
        listingService.deleteListing(user.getId(), listingId);
    }

    @GetMapping("/search")
    public List<ListingDto> search(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("checkin_date") LocalDate checkIn,
            @RequestParam("checkout_date") LocalDate checkOut,
            @RequestParam("guest_number") int guestNumber,
            @RequestParam(name = "distance", required = false, defaultValue = "500000") Integer distance
    ) {
        return listingService.search(lat, lon, distance, checkIn, checkOut, guestNumber);
    }

    @GetMapping("/{listingId}/bookings")
    public List<BookingDto> getListingBookings(@PathVariable Long listingId) {
        return bookingService.findBookingsByListingId(user.getId(), listingId);
    }
}
