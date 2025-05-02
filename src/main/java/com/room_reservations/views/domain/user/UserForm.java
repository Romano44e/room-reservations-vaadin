package com.room_reservations.views.domain.user;

import com.room_reservations.views.UsersView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class UserForm extends FormLayout {

    private final TextField name = new TextField("Name");
    private final TextField email = new TextField("Email");
    private final TextField points = new TextField("Points");
    private final PasswordField passwordField = new PasswordField("Password (for update/delete)");

    private final Button create = new Button("Create");
    private final Button update = new Button("Update");
    private final Button delete = new Button("Delete");

    private final UsersView usersView;
    private User user;

    public UserForm(UsersView usersView) {
        this.usersView = usersView;

        HorizontalLayout buttons = new HorizontalLayout(create, update, delete);
        add(name, email, points, passwordField, buttons);

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
            passwordField.clear();
        }
    }

    private void create() {
        User newUser = new User();
        newUser.setName(name.getValue());
        newUser.setEmail(email.getValue());
        newUser.setPoints(Integer.parseInt(points.getValue()));
        newUser.setPassword(passwordField.getValue());
        usersView.createUser(newUser);
    }

    private void update() {
        if (user != null && user.passwordMatches(passwordField.getValue())) {
            user.setName(name.getValue());
            user.setEmail(email.getValue());
            user.setPoints(Integer.parseInt(points.getValue()));
            user.setPassword(passwordField.getValue());
            usersView.updateUserByPassword(user);
        } else {
            Notification.show("Incorrect password. Cannot update user.");
        }
    }

    private void delete() {
        if (user != null && user.passwordMatches(passwordField.getValue())) {
            usersView.deleteUserByPassword(user.getPassword());
        } else {
            Notification.show("Incorrect password. Cannot delete user.");
        }
    }
}
