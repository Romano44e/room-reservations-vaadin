package com.room_reservations.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "home", layout = MainLayout.class)
public class HomeView extends VerticalLayout {
    public HomeView() {
        add(new H1("Welcome to the Room Reservation System"));
        add(new Paragraph("Use the navigation above to browse rooms, make reservations, manage users, or contact us for support."));
        setAlignItems(Alignment.CENTER);
    }
}
