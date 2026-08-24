package com.laioffer.staybooking.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "guest_id")
    private Long guestId;
    @Column(name = "listing_id")
    private Long listingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @ManyToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name = "listing_id", foreignKey = @ForeignKey(name = "fk_booking_listing"), insertable = false, updatable = false)
    private ListingEntity listing;

    @ManyToOne
    @OnDelete(action=OnDeleteAction.CASCADE)
    @JoinColumn(name = "guest_id", foreignKey = @ForeignKey(name = "fk_booking_guest"), insertable = false, updatable = false)
    private UserEntity guest;

    public BookingEntity() {
    }

    public BookingEntity(Long id, Long guestId, Long listingId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = id;
        this.guestId = guestId;
        this.listingId = listingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Long getId() {
        return id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public Long getListingId() {
        return listingId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public ListingEntity getListing() {
        return listing;
    }

    public UserEntity getGuest() {
        return guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingEntity that = (BookingEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(guestId, that.guestId) && Objects.equals(listingId, that.listingId) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate) && Objects.equals(listing, that.listing) && Objects.equals(guest, that.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guestId, listingId, checkInDate, checkOutDate, listing, guest);
    }

    @Override
    public String toString() {
        return "BookingEntity{" +
                "id=" + id +
                ", guestId=" + guestId +
                ", listingId=" + listingId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", listing=" + listing +
                ", guest=" + guest +
                '}';
    }
}
