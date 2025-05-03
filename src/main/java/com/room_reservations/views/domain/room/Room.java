package com.room_reservations.views.domain.room;

import com.room_reservations.views.domain.reservation.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Room {

    private String name;
    private int capacity;
    private String location;
    private BigDecimal price;
    private String cipher;
    private Set<Reservation> reservations = new HashSet<>();

    public Room() {
    }

    public Room(String name, int capacity, String location, BigDecimal price, String cipher) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.price = price;
        this.cipher = cipher;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLocation() {
        return location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isAvailable(LocalDate date, LocalTime time) {
        LocalDateTime requestedDateTime = LocalDateTime.of(date, time);
        return reservations.stream()
                .noneMatch(reservation ->
                        !requestedDateTime.isBefore(reservation.getStartTime()) &&
                                !requestedDateTime.isAfter(reservation.getEndTime())
                );
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;
        return capacity == room.capacity && Objects.equals(name, room.name) && Objects.equals(location, room.location) && Objects.equals(price, room.price);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + capacity;
        result = 31 * result + Objects.hashCode(location);
        result = 31 * result + Objects.hashCode(price);
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public String getCipher() {
        return cipher;
    }
    public void setCipher(String cipher) {
        this.cipher = cipher;
    }
}
