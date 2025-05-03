package com.room_reservations.views;

import com.room_reservations.views.domain.room.Room;
import com.room_reservations.views.domain.room.RoomForm;
import com.room_reservations.views.domain.room.RoomRestClient;
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

@Route(value = "rooms", layout = MainLayout.class)
public class RoomsView extends VerticalLayout {

    private final RoomRestClient roomClient = new RoomRestClient();
    private final Grid<Room> grid = new Grid<>(Room.class);
    private final TextField nameFilter = new TextField("Name");
    private final TextField locationFilter = new TextField("Location");
    private final TextField capacityFilter = new TextField("Capacity");
    private final TextField priceFilter = new TextField("Price");
    private final RoomForm form = new RoomForm(this);
    private final Button addNewRoom = new Button("Add new room");

    public RoomsView() {
        add(new H2("Rooms Page"));

        grid.setColumns("name", "location", "capacity", "price");

        nameFilter.setPlaceholder("Name");
        locationFilter.setPlaceholder("Location");
        capacityFilter.setPlaceholder("Capacity");
        priceFilter.setPlaceholder("Price");

        nameFilter.addValueChangeListener(e -> update());
        locationFilter.addValueChangeListener(e -> update());
        capacityFilter.addValueChangeListener(e -> update());
        priceFilter.addValueChangeListener(e -> update());

        addNewRoom.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setRoom(new Room());
        });

        HorizontalLayout filters = new HorizontalLayout(nameFilter, locationFilter, capacityFilter, priceFilter);
        HorizontalLayout toolbar = new HorizontalLayout(filters, addNewRoom);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);
        form.setRoom(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setRoom(grid.asSingleSelect().getValue()));
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

    public void createRoom(Room room) {
        roomClient.createRoom(room);
        Notification.show("Room created.");
        refresh();
    }

    public void updateRoom(Room room) {
        roomClient.updateRoom(room);
        Notification.show("Room updated.");
        refresh();
    }

    public void deleteRoomByCipher(String cipher) {
        roomClient.deleteRoomByCipher(cipher);
        Notification.show("Room deleted.");
        refresh();
    }
}



