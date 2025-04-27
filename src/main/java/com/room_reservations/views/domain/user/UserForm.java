package com.room_reservations.views.domain.user;

import com.room_reservations.views.UsersView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

public class UserForm extends FormLayout {

    private TextField name = new TextField("Name");
    private TextField email = new TextField("Email");
    private TextField points = new TextField("Points");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private UsersView usersView;
    private User user;

    public UserForm(UsersView usersView) {
        this.usersView = usersView;

        add(name, email, points, save, delete);

        save.addClickListener(event -> save());
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

    private void save() {
        if (user != null) {
            user.setName(name.getValue());
            user.setEmail(email.getValue());
            user.setPoints(Integer.parseInt(points.getValue()));
            UserService.getInstance().save(user);
            usersView.refresh();
            setUser(null);
        }
    }

    private void delete() {
        if (user != null) {
            UserService.getInstance().delete(user);
            usersView.refresh();
            setUser(null);
        }
    }
}
