package com.laioffer.staybooking.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "listings")
public class ListingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "host_id")
    private Long hostId;
    private String name;
    private String address;
    private String description;
    private Integer guestNumber;
    private List<String> imageUrls;
    private Point location;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "host_id", foreignKey = @ForeignKey(name = "fk_listing_host"), insertable = false, updatable = false)
    private UserEntity host;

    public ListingEntity() {
    }

    public ListingEntity(Long id, Long hostId, String name, String address, String description, Integer guestNumber, List<String> imageUrls, Point location
    ) {
        this.id = id;
        this.hostId = hostId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.guestNumber = guestNumber;
        this.imageUrls = imageUrls;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public Long getHostId() {
        return hostId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public Integer getGuestNumber() {
        return guestNumber;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public Point getLocation() {
        return location;
    }

    public UserEntity getHost() {
        return host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingEntity that = (ListingEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(hostId, that.hostId) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(description, that.description) && Objects.equals(guestNumber, that.guestNumber) && Objects.equals(imageUrls, that.imageUrls) && Objects.equals(location, that.location) && Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hostId, name, address, description, guestNumber, imageUrls, location, host);
    }

    @Override
    public String toString() {
        return "ListingEntity{" +
                "id=" + id +
                ", hostId=" + hostId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", guestNumber=" + guestNumber +
                ", imageUrls=" + imageUrls +
                ", location=" + location +
                ", host=" + host +
                '}';
    }
}
