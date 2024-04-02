package org.example.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    //Attributes
    private int reservationId;
    private Location location;

    private User guest;
    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal total;

    //Constructors
    public Reservation() {
    }

    public Reservation(int reservationId, Location location, User guest, LocalDate startDate, LocalDate endDate, BigDecimal total) {
        this.reservationId = reservationId;
        this.location = location;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
    }

    //Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    //Override the equals and hashCode methods.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation reservation = (Reservation) o;

        if (reservationId != reservation.reservationId) return false;
        if (!Objects.equals(location, reservation.location)) return false;
        if (!Objects.equals(guest, reservation.guest)) return false;
        if (!Objects.equals(startDate, reservation.startDate)) return false;
        if (!Objects.equals(endDate, reservation.endDate)) return false;
        if (!Objects.equals(total, reservation.total)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (guest != null ? guest.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }




}
