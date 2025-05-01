package com.room_reservations.views;

import com.room_reservations.views.domain.reservation.Reservation;
import com.room_reservations.views.domain.room.Room;
import com.room_reservations.views.domain.room.RoomForm;
import com.room_reservations.views.domain.room.RoomService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Route(value = "rooms", layout = MainLayout.class)
public class RoomsView extends VerticalLayout {

    private final RoomService roomService = RoomService.getInstance();
    private final Grid<Room> grid = new Grid<>(Room.class);
    private final TextField nameFilter = new TextField("Name");
    private final TextField capacityFilter = new TextField("Capacity");
    private final TextField locationFilter = new TextField("Location");
    private final TextField priceFilter = new TextField("Price");
    private final RoomForm form = new RoomForm(this);
    private final Button addNewRoom = new Button("Add new room");
    private final Button toggleFilters = new Button("Show/Hide Filters");
    private final VerticalLayout filterSection = new VerticalLayout();

    public RoomsView() {
        add(new H2("Rooms Page"));

        nameFilter.setClearButtonVisible(true);
        capacityFilter.setClearButtonVisible(true);
        locationFilter.setClearButtonVisible(true);
        priceFilter.setClearButtonVisible(true);

        nameFilter.setWidth("150px");
        capacityFilter.setWidth("150px");
        locationFilter.setWidth("150px");
        priceFilter.setWidth("150px");

        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        capacityFilter.setValueChangeMode(ValueChangeMode.EAGER);
        locationFilter.setValueChangeMode(ValueChangeMode.EAGER);
        priceFilter.setValueChangeMode(ValueChangeMode.EAGER);

        nameFilter.addValueChangeListener(e -> update());
        capacityFilter.addValueChangeListener(e -> update());
        locationFilter.addValueChangeListener(e -> update());
        priceFilter.addValueChangeListener(e -> update());

        grid.setColumns("name", "capacity", "location", "price");

        grid.addComponentColumn(room -> {
            Button check = new Button("Check Availability");
            check.addClickListener(e -> openAvailabilityDialog(room));
            return check;
        });

        addNewRoom.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setRoom(new Room());
        });

        HorizontalLayout filters = new HorizontalLayout(
                nameFilter, capacityFilter, locationFilter, priceFilter
        );
        filters.setAlignItems(FlexComponent.Alignment.BASELINE);
        filters.setSpacing(true);

        filterSection.add(filters);
        filterSection.setVisible(false);

        toggleFilters.addClickListener(e -> filterSection.setVisible(!filterSection.isVisible()));

        HorizontalLayout toolbar = new HorizontalLayout(toggleFilters, addNewRoom);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(new VerticalLayout(toolbar, filterSection), mainContent);
        form.setRoom(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setRoom(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(roomService.getRooms());
    }

    private void update() {
        grid.setItems(roomService.findAvailableRooms(
                nameFilter.getValue(),
                capacityFilter.getValue(),
                locationFilter.getValue(),
                priceFilter.getValue()
        ));
    }

    private void openAvailabilityDialog(Room room) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Check Availability for: " + room.getName());

        DatePicker datePicker = new DatePicker("Select Date");
        TimePicker timePicker = new TimePicker("Select Time");
        Button checkButton = new Button("Check");
        Span result = new Span();

        checkButton.addClickListener(e -> {
            LocalDate date = datePicker.getValue();
            LocalTime time = timePicker.getValue();
            if (date == null || time == null) {
                result.setText("Please select both date and time.");
                return;
            }
            boolean available = room.isAvailable(date, time);
            if (available) {
                result.setText("Room is available.");
            } else {
                result.setText("Room is already reserved at selected time.");
            }
        });

        VerticalLayout layout = new VerticalLayout(datePicker, timePicker, checkButton, result);
        dialog.add(layout);
        dialog.setWidth("400px");
        dialog.setHeight("400px");

        Button close = new Button("Close", e -> dialog.close());
        dialog.getFooter().add(close);
        dialog.open();
    }
}


