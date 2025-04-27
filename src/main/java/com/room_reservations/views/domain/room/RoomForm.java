package com.room_reservations.views.domain.room;

import com.room_reservations.views.RoomsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class RoomForm extends FormLayout {

    private RoomsView roomsView;
    private RoomService service = RoomService.getInstance();

    private TextField name = new TextField("Name");
    private TextField capacity = new TextField("Capacity");
    private ComboBox<RoomType> location = new ComboBox<>("Location");
    private TextField price = new TextField("Price");


    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Room> binder = new Binder<Room>(Room.class);

    public RoomForm(RoomsView roomsView) {
        location.setItems(RoomType.values());
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(name, capacity, location, price, buttons);
        binder.bindInstanceFields(this);
        this.roomsView = roomsView;
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
    }

    private void save() {
        Room room = binder.getBean();
        service.save(room);
        roomsView.refresh();
        setRoom(null);
    }

    private void delete() {
        Room book = binder.getBean();
        service.delete(book);
        roomsView.refresh();
        setRoom(null);
    }

    public void setRoom(Room room) {
        binder.setBean(room);

        if (room == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

}
