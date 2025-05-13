package com.room_reservations.views.domain.room;

import com.room_reservations.views.RoomsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;

public class RoomForm extends FormLayout {

    private final TextField name = new TextField("Name");
    private final TextField location = new TextField("Location");
    private final TextField capacity = new TextField("Capacity");
    private final TextField price = new TextField("Price");
    private final TextField cipher = new TextField("Cipher (for update/delete)");

    private final Button create = new Button("Create");
    private final Button update = new Button("Update");
    private final Button delete = new Button("Delete");
    private final Button closeButton = new Button("Hide Form");

    private final RoomsView roomsView;
    private Room room;

    public RoomForm(RoomsView roomsView) {
        this.roomsView = roomsView;

        closeButton.addClickListener(e -> this.setVisible(false));

        HorizontalLayout header = new HorizontalLayout(closeButton);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        HorizontalLayout buttons = new HorizontalLayout(create, update, delete);

        add(header, name, location, capacity, price, cipher, buttons);

        create.addClickListener(event -> create());
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
    }

    public void setRoom(Room room) {
        this.room = room;
        if (room != null) {
            name.setValue(room.getName() != null ? room.getName() : "");
            location.setValue(room.getLocation() != null ? room.getLocation() : "");
            capacity.setValue(String.valueOf(room.getCapacity()));
            price.setValue(String.valueOf(room.getPrice()));
            cipher.clear();
        }
    }

    private void create() {
        if (name.isEmpty() || location.isEmpty() || capacity.isEmpty() || price.isEmpty() || cipher.isEmpty()) {
            Notification.show("All fields including cipher are required to create a room.");
            return;
        }
        Room newRoom = new Room();
        newRoom.setName(name.getValue());
        newRoom.setLocation(location.getValue());
        newRoom.setCapacity(Integer.parseInt(capacity.getValue()));
        newRoom.setPrice(BigDecimal.valueOf(Double.parseDouble(price.getValue())));
        newRoom.setCipher(cipher.getValue());
        roomsView.createRoom(newRoom);
    }

    private void update() {
        if (room != null) {
            if (name.isEmpty() || location.isEmpty() || capacity.isEmpty() || price.isEmpty() || cipher.isEmpty()) {
                Notification.show("All fields including cipher are required to update a room.");
                return;
            }
            room.setName(name.getValue());
            room.setLocation(location.getValue());
            room.setCapacity(Integer.parseInt(capacity.getValue()));
            room.setPrice(BigDecimal.valueOf(Double.parseDouble(price.getValue())));
            room.setCipher(cipher.getValue());
            roomsView.updateRoom(room);
        }
    }

    private void delete() {
        if (!cipher.isEmpty()) {
            roomsView.deleteRoomByCipher(cipher.getValue());
        } else {
            Notification.show("Please enter cipher to delete.");
        }
    }

}
