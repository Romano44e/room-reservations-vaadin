package com.room_reservations.views;

import com.room_reservations.views.domain.room.Room;
import com.room_reservations.views.domain.room.RoomForm;
import com.room_reservations.views.domain.room.RoomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalTime;

@Route(value = "rooms", layout = MainLayout.class)
public class RoomsView extends VerticalLayout {

    private RoomService roomService = RoomService.getInstance();
    private Grid<Room> grid = new Grid<>(Room.class);
    private TextField nameFilter = new TextField("Name");
    private TextField capacityFilter = new TextField("Capacity");
    private TextField locationFilter = new TextField("Location");
    private TextField priceFilter = new TextField("Price");
    private DatePicker dateFilter = new DatePicker("Date");
    private TimePicker timeFilter = new TimePicker("Time");
    private RoomForm form = new RoomForm(this);
    private Button addNewRoom = new Button("Add new room");

    public RoomsView() {
        add(new H2("Rooms Page"));

        nameFilter.setClearButtonVisible(true);
        capacityFilter.setClearButtonVisible(true);
        locationFilter.setClearButtonVisible(true);
        priceFilter.setClearButtonVisible(true);

        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        capacityFilter.setValueChangeMode(ValueChangeMode.EAGER);
        locationFilter.setValueChangeMode(ValueChangeMode.EAGER);
        priceFilter.setValueChangeMode(ValueChangeMode.EAGER);

        nameFilter.addValueChangeListener(e -> update());
        capacityFilter.addValueChangeListener(e -> update());
        locationFilter.addValueChangeListener(e -> update());
        priceFilter.addValueChangeListener(e -> update());
        dateFilter.addValueChangeListener(e -> update());
        timeFilter.addValueChangeListener(e -> update());

        grid.setColumns("name", "capacity", "location", "price");

        addNewRoom.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setRoom(new Room());
        });

        HorizontalLayout filters = new HorizontalLayout(
                nameFilter, capacityFilter, locationFilter, priceFilter, dateFilter, timeFilter
        );
        HorizontalLayout toolbar = new HorizontalLayout(filters, addNewRoom);
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
        grid.setItems(roomService.getRooms());
    }

    private void update() {
        LocalDate selectedDate = dateFilter.getValue();
        LocalTime selectedTime = timeFilter.getValue();
        grid.setItems(roomService.findAvailableRooms(
                nameFilter.getValue(),
                capacityFilter.getValue(),
                locationFilter.getValue(),
                priceFilter.getValue(),
                selectedDate,
                selectedTime
        ));
    }
}
