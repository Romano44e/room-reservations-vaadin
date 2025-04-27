package com.room_reservations.views.domain.reservation;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationService {

    private static ReservationService instance;
    private Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {
        this.reservations = exampleData();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public Set<Reservation> getReservations() {
        return new HashSet<>(reservations);
    }

    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    public void updateByCode(String code, Reservation updated) {
        reservations.removeIf(r -> r.getCode().equals(code));
        reservations.add(updated);
    }

    public void deleteByCode(String code) {
        reservations.removeIf(r -> r.getCode().equals(code));
    }

    public Set<Reservation> findByFilters(String userName, String roomName, String status, String isPaid, String value) {
        return reservations.stream()
                .filter(r -> (userName == null || r.getUserName().toLowerCase().contains(userName.toLowerCase()))
                        && (roomName == null || r.getRoomName().toLowerCase().contains(roomName.toLowerCase()))
                        && (status == null || r.getStatus().toLowerCase().contains(status.toLowerCase()))
                        && (isPaid == null || r.getIsPaid().toLowerCase().contains(isPaid.toLowerCase()))
                        && (value == null || r.getValue().toLowerCase().contains(value.toLowerCase())))
                .collect(Collectors.toSet());
    }

    private Set<Reservation> exampleData() {
        Set<Reservation> examples = new HashSet<>();
        examples.add(new Reservation("Piotr Nowak", "Jupiter Room", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Confirmed", "paid", "450.00", "ABC123"));
        examples.add(new Reservation("Andrzej Wiśniewski", "Saturn Room", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), "Pending", "unpaid", "400.00", "DEF456"));
        examples.add(new Reservation("Anna Kowalska", "Mars Room", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(2), "Cancelled", "unpaid", "500.00", "GHI789"));
        return examples;
    }
}
