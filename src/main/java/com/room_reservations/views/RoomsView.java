package com.room_reservations.views;

import com.room_reservations.views.domain.room.*;
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
import java.util.List;

@Route(value = "rooms", layout = MainLayout.class)
public class RoomsView extends VerticalLayout {

    private final RoomRestClient roomClient = new RoomRestClient();
    private final Grid<Room> grid = new Grid<>(Room.class);
    private final TextField locationFilter = new TextField("Location");
    private final TextField capacityFilter = new TextField("Capacity");
    private final TextField priceFilter = new TextField("Price");

    private final TextField roomNameFilter = new TextField("Room Name");
    private final DateTimePicker startDateTime = new DateTimePicker("Start DateTime");
    private final DateTimePicker endDateTime = new DateTimePicker("End DateTime");
    private final Button checkAvailability = new Button("Show/Hide Availability");
    private final Button runAvailabilityCheck = new Button("Check Availability");

    private final RoomForm form = new RoomForm(this);
    private final Button addNewRoom = new Button("Add new room");
    private final Button toggleFilters = new Button("Show/Hide Filters");

    private final HorizontalLayout filters = new HorizontalLayout();
    private final HorizontalLayout availabilityFilters = new HorizontalLayout();

    public RoomsView() {
        add(new H2("Rooms Page"));

        grid.setColumns("name", "location", "capacity", "price", "cipher");

        locationFilter.setPlaceholder("Location");
        capacityFilter.setPlaceholder("Capacity");
        priceFilter.setPlaceholder("Price");
        roomNameFilter.setPlaceholder("Room Name");

        locationFilter.setValueChangeMode(ValueChangeMode.EAGER);
        capacityFilter.setValueChangeMode(ValueChangeMode.EAGER);
        priceFilter.setValueChangeMode(ValueChangeMode.EAGER);

        locationFilter.addValueChangeListener(e -> update());
        capacityFilter.addValueChangeListener(e -> update());
        priceFilter.addValueChangeListener(e -> update());

        runAvailabilityCheck.addClickListener(e -> checkAvailability());

        addNewRoom.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setRoom(new Room());
            form.setVisible(true);
        });

        filters.add(locationFilter, capacityFilter, priceFilter);
        filters.setVisible(false);

        availabilityFilters.add(roomNameFilter, startDateTime, endDateTime, runAvailabilityCheck);
        availabilityFilters.setVisible(false);

        toggleFilters.addClickListener(e -> filters.setVisible(!filters.isVisible()));
        checkAvailability.addClickListener(e -> availabilityFilters.setVisible(!availabilityFilters.isVisible()));

        HorizontalLayout toolbar = new HorizontalLayout(toggleFilters, checkAvailability, addNewRoom);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        grid.setSizeFull();
        form.setVisible(false);

        add(toolbar, filters, availabilityFilters, grid, form);
        form.setRoom(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setRoom(grid.asSingleSelect().getValue());
            form.setVisible(true);
        });
    }

    public void refresh() {
        List<Room> rooms = roomClient.getAllRooms();
        grid.setItems(rooms);
    }

    private void update() {
        if (!locationFilter.isEmpty()) {
            grid.setItems(roomClient.getRoomsByLocation(locationFilter.getValue()));
        } else if (!capacityFilter.isEmpty()) {
            grid.setItems(roomClient.getRoomsByCapacity(capacityFilter.getValue()));
        } else if (!priceFilter.isEmpty()) {
            grid.setItems(roomClient.getRoomsByPrice(priceFilter.getValue()));
        } else {
            refresh();
        }
    }

    private void checkAvailability() {
        LocalDateTime start = startDateTime.getValue();
        LocalDateTime end = endDateTime.getValue();
        String roomName = roomNameFilter.getValue();

        if (start == null || end == null || roomName == null || roomName.isEmpty()) {
            Notification.show("Please provide room name, start and end datetime.");
            return;
        }

        if (start.isAfter(end)) {
            Notification.show("Start datetime must be before end datetime.");
            return;
        }

        RoomByDateTimeInputDto dto = new RoomByDateTimeInputDto(roomName, start, end);
        String result = roomClient.isRoomAvailableViaPost(dto);
        Notification.show(result);
    }

    public void createRoom(Room room) {
        roomClient.createRoom(room);
        Notification.show("Room created.");
        refresh();
    }

    public void updateRoom(Room room) {
        roomClient.updateRoomByCipher(room);
        Notification.show("Room updated.");
        refresh();
    }

    public void deleteRoomByCipher(String cipher) {
        roomClient.deleteRoomByCipher(cipher);
        Notification.show("Room deleted.");
        refresh();
    }
}



