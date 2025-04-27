package com.room_reservations.views.domain.reservation;

import com.room_reservations.views.ReservationsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;

public class ReservationForm extends FormLayout {

    private TextField userName = new TextField("User Name");
    private TextField roomName = new TextField("Room Name");
    private DateTimePicker startTime = new DateTimePicker("Start Time");
    private DateTimePicker endTime = new DateTimePicker("End Time");
    private TextField status = new TextField("Status");
    private TextField isPaid = new TextField("Payment Status (paid/unpaid)");
    private TextField value = new TextField("Value");
    private TextField code = new TextField("Reservation Code");

    private Button save = new Button("Save");
    private Button update = new Button("Update by Code");
    private Button delete = new Button("Delete by Code");

    private ReservationsView reservationsView;
    private Reservation reservation;

    public ReservationForm(ReservationsView reservationsView) {
        this.reservationsView = reservationsView;

        add(userName, roomName, startTime, endTime, status, isPaid, value, code, save, update, delete);

        save.addClickListener(e -> save());
        update.addClickListener(e -> update());
        delete.addClickListener(e -> delete());
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if (reservation != null) {
            userName.setValue(reservation.getUserName() != null ? reservation.getUserName() : "");
            roomName.setValue(reservation.getRoomName() != null ? reservation.getRoomName() : "");
            startTime.setValue(reservation.getStartTime() != null ? reservation.getStartTime() : LocalDateTime.now());
            endTime.setValue(reservation.getEndTime() != null ? reservation.getEndTime() : LocalDateTime.now().plusHours(1));
            status.setValue(reservation.getStatus() != null ? reservation.getStatus() : "");
            isPaid.setValue(reservation.getIsPaid() != null ? reservation.getIsPaid() : "");
            value.setValue(reservation.getValue() != null ? reservation.getValue() : "");
            code.setValue(reservation.getCode() != null ? reservation.getCode() : "");
        }
    }

    private void save() {
        Reservation r = new Reservation(
                userName.getValue(),
                roomName.getValue(),
                startTime.getValue(),
                endTime.getValue(),
                status.getValue(),
                isPaid.getValue(),
                value.getValue(),
                code.getValue()
        );
        ReservationService.getInstance().save(r);
        reservationsView.refresh();
    }

    private void update() {
        Reservation r = new Reservation(
                userName.getValue(),
                roomName.getValue(),
                startTime.getValue(),
                endTime.getValue(),
                status.getValue(),
                isPaid.getValue(),
                value.getValue(),
                code.getValue()
        );
        ReservationService.getInstance().updateByCode(code.getValue(), r);
        reservationsView.refresh();
    }

    private void delete() {
        ReservationService.getInstance().deleteByCode(code.getValue());
        reservationsView.refresh();
    }
}


