package com.room_reservations.views.domain.reservation;

import java.time.LocalDateTime;

public class Reservation {

    private String userName;
    private String roomName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String isPaid;
    private String value;
    private String code;

    public Reservation() {}

    public Reservation(String userName, String roomName, LocalDateTime startTime, LocalDateTime endTime, String status, String isPaid, String value, String code) {
        this.userName = userName;
        this.roomName = roomName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.isPaid = isPaid;
        this.value = value;
        this.code = code;
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getIsPaid() { return isPaid; }
    public void setIsPaid(String isPaid) { this.isPaid = isPaid; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
