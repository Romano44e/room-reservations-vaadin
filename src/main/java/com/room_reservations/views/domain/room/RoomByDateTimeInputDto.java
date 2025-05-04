package com.room_reservations.views.domain.room;

import java.time.LocalDateTime;

public class RoomByDateTimeInputDto {

    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public RoomByDateTimeInputDto() {
    }

    public RoomByDateTimeInputDto(String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

}
