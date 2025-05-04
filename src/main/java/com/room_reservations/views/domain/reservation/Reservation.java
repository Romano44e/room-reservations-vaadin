package com.room_reservations.views.domain.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Reservation {

    private Long userId;
    private Long roomId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String reservationStatus;
    private String paymentStatus;
    private String currency;
    private BigDecimal amount;
    private String code;

    public Reservation() {}

    public Reservation(Long userId, Long roomId, LocalDateTime startDateTime, LocalDateTime endDateTime, String reservationStatus, String paymentStatus, String currency, BigDecimal amount, String code) {
        this.userId = userId;
        this.roomId = roomId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.reservationStatus = reservationStatus;
        this.paymentStatus = paymentStatus;
        this.currency = currency;
        this.amount = amount;
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
