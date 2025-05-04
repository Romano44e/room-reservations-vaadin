package com.room_reservations.views;

import com.room_reservations.views.domain.reservation.Reservation;
import com.room_reservations.views.domain.reservation.ReservationForm;
import com.room_reservations.views.domain.reservation.ReservationRestClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "reservations", layout = MainLayout.class)
public class ReservationsView extends VerticalLayout {

    private final ReservationRestClient reservationClient = new ReservationRestClient();
    private final Grid<Reservation> grid = new Grid<>(Reservation.class);
    private final TextField userFilter = new TextField("User ID");
    private final TextField roomFilter = new TextField("Room ID");
    private final TextField statusFilter = new TextField("Status");
    private final TextField paymentFilter = new TextField("Payment Status");
    private final TextField amountFilter = new TextField("Amount");
    private final TextField codeFilter = new TextField("Reservation Code");
    private final TextField dateTimeFilter = new TextField("Start or End DateTime (YYYY-MM-DDTHH:MM)");

    private final ReservationForm form = new ReservationForm(this);
    private final Button addNewReservation = new Button("Add new reservation");
    private final Button toggleFilters = new Button("Show/Hide Filters");

    private final HorizontalLayout filters = new HorizontalLayout();

    public ReservationsView() {
        add(new H2("Reservations Page"));

        grid.setColumns("userId", "roomId", "startDateTime", "endDateTime", "reservationStatus", "paymentStatus", "currency", "amount", "code");

        userFilter.setPlaceholder("User ID");
        roomFilter.setPlaceholder("Room ID");
        statusFilter.setPlaceholder("Status");
        paymentFilter.setPlaceholder("Payment");
        amountFilter.setPlaceholder("Amount");
        codeFilter.setPlaceholder("Code");
        dateTimeFilter.setPlaceholder("DateTime (YYYY-MM-DDTHH:MM)");

        userFilter.addValueChangeListener(e -> update());
        roomFilter.addValueChangeListener(e -> update());
        statusFilter.addValueChangeListener(e -> update());
        paymentFilter.addValueChangeListener(e -> update());
        amountFilter.addValueChangeListener(e -> update());
        dateTimeFilter.addValueChangeListener(e -> update());

        addNewReservation.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setReservation(new Reservation());
            form.setVisible(true);
        });

        filters.add(userFilter, roomFilter, statusFilter, paymentFilter, amountFilter, dateTimeFilter);
        filters.setVisible(false);

        toggleFilters.addClickListener(e -> filters.setVisible(!filters.isVisible()));

        HorizontalLayout toolbar = new HorizontalLayout(toggleFilters, addNewReservation);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        grid.setSizeFull();
        form.setVisible(false);

        add(toolbar, filters, grid, form);
        form.setReservation(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setReservation(grid.asSingleSelect().getValue());
            form.setVisible(true);
        });
    }

    public void refresh() {
        List<Reservation> reservations = reservationClient.getAllReservations();
        grid.setItems(reservations);
    }

    private void update() {
        if (!userFilter.isEmpty()) {
            grid.setItems(reservationClient.getByUserId(userFilter.getValue()));
        } else if (!roomFilter.isEmpty()) {
            grid.setItems(reservationClient.getByRoomId(roomFilter.getValue()));
        } else if (!statusFilter.isEmpty()) {
            grid.setItems(reservationClient.getByReservationStatus(statusFilter.getValue()));
        } else if (!paymentFilter.isEmpty()) {
            grid.setItems(reservationClient.getByPaymentStatus(paymentFilter.getValue()));
        } else if (!amountFilter.isEmpty()) {
            grid.setItems(reservationClient.getByAmount(amountFilter.getValue()));
        } else if (!dateTimeFilter.isEmpty()) {
            grid.setItems(reservationClient.getByDateTime(dateTimeFilter.getValue()));
        } else {
            refresh();
        }
    }

    public void createReservation(Reservation reservation) {
        reservationClient.createReservation(reservation);
        Notification.show("Reservation created.");
        refresh();
    }

    public void updateReservation(Reservation reservation) {
        reservationClient.updateReservationByCode(reservation);
        Notification.show("Reservation updated.");
        refresh();
    }

    public void deleteReservation(String code) {
        reservationClient.deleteReservationByCode(code);
        Notification.show("Reservation deleted.");
        refresh();
    }
}



