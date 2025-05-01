package com.room_reservations.views.domain.user;

import com.room_reservations.views.UsersView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class UserForm extends FormLayout {

    private final TextField name = new TextField("Name");
    private final TextField email = new TextField("Email");
    private final TextField points = new TextField("Points");

    private final Button create = new Button("Create");
    private final Button update = new Button("Update");
    private final Button delete = new Button("Delete");

    private final UsersView usersView;
    private User user;

    public UserForm(UsersView usersView) {
        this.usersView = usersView;

        HorizontalLayout buttons = new HorizontalLayout(create, update, delete);
        add(name, email, points, buttons);

        create.addClickListener(event -> create());
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            name.setValue(user.getName() != null ? user.getName() : "");
            email.setValue(user.getEmail() != null ? user.getEmail() : "");
            points.setValue(String.valueOf(user.getPoints()));
        }
    }

    private void create() {
        User newUser = new User();
        newUser.setName(name.getValue());
        newUser.setEmail(email.getValue());
        newUser.setPoints(Integer.parseInt(points.getValue()));
        usersView.createUser(newUser);
    }

    private void update() {
        if (user != null) {
            user.setName(name.getValue());
            user.setEmail(email.getValue());
            user.setPoints(Integer.parseInt(points.getValue()));
            usersView.updateUser(user);
        }
    }

    private void delete() {
        usersView.deleteUser(user);
    }
}
