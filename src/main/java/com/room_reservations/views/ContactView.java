package com.room_reservations.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "contact", layout = MainLayout.class)
public class ContactView extends VerticalLayout {
    public ContactView() {
        add(new H1("Contact Us"));
        add(new Paragraph("If you have any questions, feel free to reach out to us at support@example.com or call us at +48 123 456 789."));
        setAlignItems(Alignment.CENTER);
    }
}
