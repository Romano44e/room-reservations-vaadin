package com.room_reservations.views.domain.reservation;

import com.room_reservations.views.ReservationsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationForm extends FormLayout {

    private final TextField userId = new TextField("User ID");
    private final TextField roomId = new TextField("Room ID");
    private final TextField startDateTime = new TextField("Start Time (YYYY-MM-DDTHH:MM)");
    private final TextField endDateTime = new TextField("End Time (YYYY-MM-DDTHH:MM)");
    private final TextField reservationStatus = new TextField("Reservation Status");
    private final TextField paymentStatus = new TextField("Payment Status");
    private final TextField currency = new TextField("Currency");
    private final TextField amount = new TextField("Amount");
    private final TextField code = new TextField("Reservation Code");

    private final Button create = new Button("Create");
    private final Button update = new Button("Update");
    private final Button delete = new Button("Delete");

    private final ReservationsView reservationsView;
    private Reservation reservation;

    public ReservationForm(ReservationsView reservationsView) {
        this.reservationsView = reservationsView;

        HorizontalLayout buttons = new HorizontalLayout(create, update, delete);
        add(userId, roomId, startDateTime, endDateTime, reservationStatus, paymentStatus, currency, amount, code, buttons);

        create.addClickListener(event -> create());
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            userId.setValue(reservation.getUserId() != null ? reservation.getUserId().toString() : "");
            roomId.setValue(reservation.getRoomId() != null ? reservation.getRoomId().toString() : "");
            startDateTime.setValue(reservation.getStartDateTime() != null ? reservation.getStartDateTime().toString() : "");
            endDateTime.setValue(reservation.getEndDateTime() != null ? reservation.getEndDateTime().toString() : "");
            reservationStatus.setValue(reservation.getReservationStatus() != null ? reservation.getReservationStatus() : "");
            paymentStatus.setValue(reservation.getPaymentStatus() != null ? reservation.getPaymentStatus() : "");
            currency.setValue(reservation.getCurrency() != null ? reservation.getCurrency() : "");
            amount.setValue(reservation.getAmount() != null ? reservation.getAmount().toString() : "");
            code.setValue(reservation.getCode() != null ? reservation.getCode() : "");
        }
    }

    private void create() {
        Reservation newReservation = new Reservation();
        newReservation.setUserId(Long.parseLong(userId.getValue()));
        newReservation.setRoomId(Long.parseLong(roomId.getValue()));
        newReservation.setStartDateTime(LocalDateTime.parse(startDateTime.getValue()));
        newReservation.setEndDateTime(LocalDateTime.parse(endDateTime.getValue()));
        newReservation.setReservationStatus(reservationStatus.getValue());
        newReservation.setPaymentStatus(paymentStatus.getValue());
        newReservation.setCurrency(currency.getValue());
        newReservation.setAmount(new BigDecimal(amount.getValue()));
        newReservation.setCode(code.getValue());
        reservationsView.createReservation(newReservation);
    }

    private void update() {
        if (reservation != null && code.getValue().equals(reservation.getCode())) {
            reservation.setUserId(Long.parseLong(userId.getValue()));
            reservation.setRoomId(Long.parseLong(roomId.getValue()));
            reservation.setStartDateTime(LocalDateTime.parse(startDateTime.getValue()));
            reservation.setEndDateTime(LocalDateTime.parse(endDateTime.getValue()));
            reservation.setReservationStatus(reservationStatus.getValue());
            reservation.setPaymentStatus(paymentStatus.getValue());
            reservation.setCurrency(currency.getValue());
            reservation.setAmount(new BigDecimal(amount.getValue()));
            reservationsView.updateReservation(reservation);
        } else {
            Notification.show("Invalid code. Cannot update.");
        }
    }

    private void delete() {
        if (code.getValue() != null && !code.getValue().isEmpty()) {
            reservationsView.deleteReservation(code.getValue());
        } else {
            Notification.show("Please enter a valid code to delete reservation.");
        }
    }
}


