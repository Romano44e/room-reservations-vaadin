package com.room_reservations.views;

import com.room_reservations.views.domain.reservation.Reservation;
import com.room_reservations.views.domain.reservation.ReservationForm;
import com.room_reservations.views.domain.reservation.ReservationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;

@Route(value = "reservations", layout = MainLayout.class)
public class ReservationsView extends VerticalLayout {

    private ReservationService reservationService = ReservationService.getInstance();
    private Grid<Reservation> grid = new Grid<>(Reservation.class);
    private TextField userFilter = new TextField("User Name");
    private TextField roomFilter = new TextField("Room Name");
    private TextField statusFilter = new TextField("Status");
    private TextField isPaidFilter = new TextField("Payment Status");
    private TextField valueFilter = new TextField("Value");
    private DateTimePicker startTimeFilter = new DateTimePicker("Start After");
    private DateTimePicker endTimeFilter = new DateTimePicker("End Before");

    private ReservationForm form = new ReservationForm(this);
    private Button addNewReservation = new Button("Add new reservation");

    public ReservationsView() {
        add(new H2("Reservations Page"));

        userFilter.setClearButtonVisible(true);
        roomFilter.setClearButtonVisible(true);
        statusFilter.setClearButtonVisible(true);
        isPaidFilter.setClearButtonVisible(true);
        valueFilter.setClearButtonVisible(true);

        userFilter.setValueChangeMode(ValueChangeMode.EAGER);
        roomFilter.setValueChangeMode(ValueChangeMode.EAGER);
        statusFilter.setValueChangeMode(ValueChangeMode.EAGER);
        isPaidFilter.setValueChangeMode(ValueChangeMode.EAGER);
        valueFilter.setValueChangeMode(ValueChangeMode.EAGER);

        userFilter.addValueChangeListener(e -> update());
        roomFilter.addValueChangeListener(e -> update());
        statusFilter.addValueChangeListener(e -> update());
        isPaidFilter.addValueChangeListener(e -> update());
        valueFilter.addValueChangeListener(e -> update());
        startTimeFilter.addValueChangeListener(e -> update());
        endTimeFilter.addValueChangeListener(e -> update());

        grid.setColumns("userName", "roomName", "startTime", "endTime", "status", "isPaid", "value", "code");

        addNewReservation.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setReservation(new Reservation());
        });

        HorizontalLayout filters = new HorizontalLayout(userFilter, roomFilter, statusFilter, isPaidFilter, valueFilter, startTimeFilter, endTimeFilter);
        HorizontalLayout toolbar = new HorizontalLayout(filters, addNewReservation);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);

        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);
        form.setReservation(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setReservation(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(reservationService.getReservations());
    }

    private void update() {
        grid.setItems(reservationService.getReservations().stream()
                .filter(r -> (userFilter.getValue().isEmpty() || r.getUserName().toLowerCase().contains(userFilter.getValue().toLowerCase()))
                        && (roomFilter.getValue().isEmpty() || r.getRoomName().toLowerCase().contains(roomFilter.getValue().toLowerCase()))
                        && (statusFilter.getValue().isEmpty() || r.getStatus().toLowerCase().contains(statusFilter.getValue().toLowerCase()))
                        && (isPaidFilter.getValue().isEmpty() || r.getIsPaid().toLowerCase().contains(isPaidFilter.getValue().toLowerCase()))
                        && (valueFilter.getValue().isEmpty() || r.getValue().toLowerCase().contains(valueFilter.getValue().toLowerCase()))
                        && (startTimeFilter.getValue() == null || r.getStartTime().isAfter(startTimeFilter.getValue()))
                        && (endTimeFilter.getValue() == null || r.getEndTime().isBefore(endTimeFilter.getValue()))
                ).toList());
    }
}
