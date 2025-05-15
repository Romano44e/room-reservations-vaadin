package com.room_reservations.views.domain.reservation;

import com.room_reservations.views.ReservationsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
    private final Button hideButton = new Button("Hide Form");

    private final ReservationsView reservationsView;
    private Reservation reservation;

    public ReservationForm(ReservationsView reservationsView) {
        this.reservationsView = reservationsView;

        hideButton.addClickListener(e -> this.setVisible(false));
        HorizontalLayout header = new HorizontalLayout(hideButton);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        HorizontalLayout buttons = new HorizontalLayout(create, update, delete);

        add(header, userId, roomId, startDateTime, endDateTime,
                reservationStatus, paymentStatus, currency, amount, code, buttons);

        create.addClickListener(event -> create());
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        boolean isCreateMode = (reservation != null && reservation.getCode() == null);

        userId.setVisible(isCreateMode);
        roomId.setVisible(true);
        startDateTime.setVisible(true);
        endDateTime.setVisible(true);
        currency.setVisible(true);

        reservationStatus.setVisible(!isCreateMode);
        paymentStatus.setVisible(!isCreateMode);
        amount.setVisible(!isCreateMode);

        create.setVisible(isCreateMode);
        update.setVisible(!isCreateMode);
        delete.setVisible(!isCreateMode);

        if (isCreateMode) {
            code.setVisible(false);
        } else {
            code.setVisible(true);
            code.clear();
        }

        if (reservation == null) {
            userId.clear();
            roomId.clear();
            startDateTime.clear();
            endDateTime.clear();
            reservationStatus.clear();
            paymentStatus.clear();
            currency.clear();
            amount.clear();
            code.clear();
        } else {
            userId.setValue(reservation.getUserId() != null ? reservation.getUserId().toString() : "");
            roomId.setValue(reservation.getRoomId() != null ? reservation.getRoomId().toString() : "");
            startDateTime.setValue(reservation.getStartDateTime() != null ? reservation.getStartDateTime().toString() : "");
            endDateTime.setValue(reservation.getEndDateTime() != null ? reservation.getEndDateTime().toString() : "");
            reservationStatus.setValue(reservation.getReservationStatus() != null ? reservation.getReservationStatus() : "");
            paymentStatus.setValue(reservation.getPaymentStatus() != null ? reservation.getPaymentStatus() : "");
            currency.setValue(reservation.getCurrency() != null ? reservation.getCurrency() : "");
            amount.setValue(reservation.getAmount() != null ? reservation.getAmount().toString() : "");
        }
    }

    private void create() {
        if (userId.isEmpty() || roomId.isEmpty() || startDateTime.isEmpty() || endDateTime.isEmpty()) {
            Notification.show("User ID, Room ID, Start and End Time are required.");
            return;
        }

        Reservation newReservation = new Reservation();
        newReservation.setUserId(Long.parseLong(userId.getValue()));
        newReservation.setRoomId(Long.parseLong(roomId.getValue()));
        newReservation.setStartDateTime(LocalDateTime.parse(startDateTime.getValue()));
        newReservation.setEndDateTime(LocalDateTime.parse(endDateTime.getValue()));
        newReservation.setCurrency(currency.getValue());

        newReservation.setReservationStatus("PENDING");
        newReservation.setPaymentStatus("UNPAID");
        newReservation.setAmount(BigDecimal.ZERO);

        reservationsView.createReservation(newReservation);
    }

    private void update() {
        if (reservation != null) {
            if (code.isEmpty()) {
                Notification.show("Reservation code is required to update.");
                return;
            }
            reservation.setUserId(Long.parseLong(userId.getValue()));
            reservation.setRoomId(Long.parseLong(roomId.getValue()));
            reservation.setStartDateTime(LocalDateTime.parse(startDateTime.getValue()));
            reservation.setEndDateTime(LocalDateTime.parse(endDateTime.getValue()));
            reservation.setReservationStatus(reservationStatus.getValue());
            reservation.setPaymentStatus(paymentStatus.getValue());
            reservation.setCurrency(currency.getValue());
            reservation.setAmount(new BigDecimal(amount.getValue()));
            reservation.setCode(code.getValue());
            reservationsView.updateReservation(reservation);
        }
    }

    private void delete() {
        if (code.isEmpty()) {
            Notification.show("Reservation code is required to delete.");
            return;
        }
        reservationsView.deleteReservation(code.getValue());
    }
}


