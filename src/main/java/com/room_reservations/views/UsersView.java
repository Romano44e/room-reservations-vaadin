package com.room_reservations.views;

import com.room_reservations.views.domain.user.User;
import com.room_reservations.views.domain.user.UserForm;
import com.room_reservations.views.domain.user.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "users", layout = MainLayout.class)
public class UsersView extends VerticalLayout {

    private UserService userService = UserService.getInstance();
    private Grid<User> grid = new Grid<>(User.class);
    private TextField nameFilter = new TextField("Name");
    private TextField emailFilter = new TextField("Email");
    private UserForm form = new UserForm(this);
    private Button addNewUser = new Button("Add new user");

    public UsersView() {
        add(new H2("Users Page"));

        nameFilter.setClearButtonVisible(true);
        emailFilter.setClearButtonVisible(true);

        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        emailFilter.setValueChangeMode(ValueChangeMode.EAGER);

        nameFilter.addValueChangeListener(e -> update());
        emailFilter.addValueChangeListener(e -> update());

        grid.setColumns("name", "email", "points");

        addNewUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setUser(new User());
        });

        HorizontalLayout filters = new HorizontalLayout(nameFilter, emailFilter);
        HorizontalLayout toolbar = new HorizontalLayout(filters, addNewUser);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);

        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);
        form.setUser(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setUser(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(userService.getUsers());
    }

    private void update() {
        grid.setItems(userService.findByNameOrEmail(
                nameFilter.getValue(),
                emailFilter.getValue()
        ));
    }
}
