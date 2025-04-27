package com.room_reservations.views.domain.room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoomService {

    private Set<Room> rooms;
    private static RoomService roomService;

    private RoomService() {
        this.rooms = exampleData();
    }

    public static RoomService getInstance() {
        if (roomService == null) {
            roomService = new RoomService();
        }
        return roomService;
    }

    public Set<Room> getRooms() {
        return new HashSet<>(rooms);
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    private Set<Room> exampleData() {
        Set<Room> rooms = new HashSet<>();
        rooms.add(new Room("Jupiter Room", 600, RoomType.CRACOW, new BigDecimal(450.00)));
        rooms.add(new Room("Saturn Room", 500, RoomType.GDANSK, new BigDecimal(400.00)));
        return rooms;
    }

    public Set<Room> findAvailableRooms(String name, String capacity, String location, String price,
                                        LocalDate date, LocalTime time) {
        return rooms.stream().filter(room -> {
            boolean matches = true;
            if (name != null && !name.isEmpty()) {
                matches &= room.getName().toLowerCase().contains(name.toLowerCase());
            }
            if (capacity != null && !capacity.isEmpty()) {
                matches &= Integer.toString(room.getCapacity()).contains(capacity);
            }
            if (location != null && !location.isEmpty()) {
                matches &= room.getLocation().toString().toLowerCase().contains(location.toLowerCase());
            }
            if (price != null && !price.isEmpty()) {
                matches &= room.getPrice().toString().contains(price);
            }
            if (date != null && time != null) {
                matches &= room.isAvailable(date, time);
            }
            return matches;
        }).collect(Collectors.toSet());
    }

    public void save(Room room) {
        this.rooms.add(room);
    }

    public void delete(Room room) {
        this.rooms.remove(room);
    }
}
