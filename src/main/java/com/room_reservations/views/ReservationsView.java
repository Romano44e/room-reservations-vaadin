package com.room_reservations.views;

import com.room_reservations.views.domain.reservation.Reservation;
import com.room_reservations.views.domain.reservation.ReservationForm;
import com.room_reservations.views.domain.reservation.ReservationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDateTime;

@Route(value = "reservations", layout = MainLayout.class)
public class ReservationsView extends VerticalLayout {

    private final ReservationService reservationService = ReservationService.getInstance();
    private final Grid<Reservation> grid = new Grid<>(Reservation.class);
    private final TextField userFilter = new TextField("User Name");
    private final TextField roomFilter = new TextField("Room Name");
    private final TextField statusFilter = new TextField("Status");
    private final TextField isPaidFilter = new TextField("Payment Status");
    private final TextField valueFilter = new TextField("Value");
    private final DateTimePicker startTimeFilter = new DateTimePicker("Start After");
    private final DateTimePicker endTimeFilter = new DateTimePicker("End Before");
    private final TextField reservationCodeInput = new TextField("Enter Reservation Code to Edit/Delete");
    private final ReservationForm form = new ReservationForm(this);
    private final Button addNewReservation = new Button("Add new reservation");
    private final Button toggleFilters = new Button("Show/Hide Filters");

    private final VerticalLayout filterSection = new VerticalLayout();

    public ReservationsView() {
        add(new H2("Reservations Page"));

        configureFilters();
        configureGrid();

        HorizontalLayout topFilters = new HorizontalLayout(userFilter, roomFilter, statusFilter, isPaidFilter, valueFilter);
        HorizontalLayout bottomFilters = new HorizontalLayout(startTimeFilter, endTimeFilter);

        topFilters.setAlignItems(FlexComponent.Alignment.BASELINE);
        bottomFilters.setAlignItems(FlexComponent.Alignment.BASELINE);

        filterSection.add(reservationCodeInput, topFilters, bottomFilters);
        filterSection.setVisible(false);

        toggleFilters.addClickListener(e -> filterSection.setVisible(!filterSection.isVisible()));

        HorizontalLayout toolbar = new HorizontalLayout(toggleFilters, addNewReservation);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);

        add(new VerticalLayout(toolbar, filterSection), grid, form);

        form.setVisible(false);
        setSizeFull();
        refresh();
    }

    private void configureFilters() {
        userFilter.setClearButtonVisible(true);
        roomFilter.setClearButtonVisible(true);
        statusFilter.setClearButtonVisible(true);
        isPaidFilter.setClearButtonVisible(true);
        valueFilter.setClearButtonVisible(true);

        userFilter.setWidth("150px");
        roomFilter.setWidth("150px");
        statusFilter.setWidth("150px");
        isPaidFilter.setWidth("150px");
        valueFilter.setWidth("150px");
        startTimeFilter.setWidth("300px");
        endTimeFilter.setWidth("300px");

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
    }

    private void configureGrid() {
        grid.setColumns("userName", "roomName", "startTime", "endTime", "status", "isPaid", "value", "code");
        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            Reservation selected = grid.asSingleSelect().getValue();
            if (selected != null) {
                String enteredCode = reservationCodeInput.getValue();
                if (enteredCode.equals(selected.getCode())) {
                    form.setReservation(selected);
                    form.enableEditing();
                    form.setVisible(true);
                } else {
                    Notification.show("Please enter the correct reservation code to edit/delete.", 4000, Notification.Position.MIDDLE);
                    form.setVisible(false);
                }
            } else {
                form.setVisible(false);
            }
        });

        addNewReservation.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setReservation(new Reservation());
            form.enableEditing();
            form.setVisible(true);
        });
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



