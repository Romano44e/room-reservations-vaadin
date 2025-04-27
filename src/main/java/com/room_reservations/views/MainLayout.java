package com.room_reservations.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.Route;

@Route("/")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
    }

    private void createHeader() {
        HorizontalLayout header = new HorizontalLayout();

        header.add(
                new RouterLink("Home", HomeView.class),
                new RouterLink("Rooms", RoomsView.class),
                new RouterLink("Reservations", ReservationsView.class),
                new RouterLink("Users", UsersView.class),
                new RouterLink("Contact", ContactView.class)
        );

        addToNavbar(header);
    }
}

