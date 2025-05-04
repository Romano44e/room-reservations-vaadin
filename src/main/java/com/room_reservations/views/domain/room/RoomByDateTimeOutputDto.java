package com.room_reservations.views.domain.room;

public class RoomByDateTimeOutputDto {

    private String availibility;

    public RoomByDateTimeOutputDto() {
    }

    public RoomByDateTimeOutputDto(String availibility) {
        this.availibility = availibility;
    }

    public String getAvailibility() {
        return availibility;
    }

    public void setAvailibility(String availibility) {
        this.availibility = availibility;
    }

}
