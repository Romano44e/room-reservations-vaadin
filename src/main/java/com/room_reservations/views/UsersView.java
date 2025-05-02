package com.room_reservations.views;

import com.room_reservations.views.domain.user.User;
import com.room_reservations.views.domain.user.UserForm;
import com.room_reservations.views.domain.user.UserRestClient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "users", layout = MainLayout.class)
public class UsersView extends VerticalLayout {

    private final UserRestClient userClient = new UserRestClient();
    private final Grid<User> grid = new Grid<>(User.class);
    private final TextField nameFilter = new TextField("Name");
    private final TextField emailFilter = new TextField("Email");
    private final UserForm form = new UserForm(this);
    private final Button addNewUser = new Button("Add new user");
    private final Button toggleFilters = new Button("Show/Hide Filters");
    private final VerticalLayout filterSection = new VerticalLayout();

    public UsersView() {
        add(new H2("Users Page"));

        nameFilter.setClearButtonVisible(true);
        emailFilter.setClearButtonVisible(true);

        nameFilter.setWidth("150px");
        emailFilter.setWidth("150px");

        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        emailFilter.setValueChangeMode(ValueChangeMode.EAGER);

        nameFilter.addValueChangeListener(e -> update());
        emailFilter.addValueChangeListener(e -> update());

        grid.setColumns("name", "email", "points");

        addNewUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setUser(new User());
        });

        HorizontalLayout topFilters = new HorizontalLayout(nameFilter, emailFilter);
        topFilters.setAlignItems(FlexComponent.Alignment.BASELINE);
        topFilters.setSpacing(true);

        filterSection.add(topFilters);
        filterSection.setVisible(false);

        toggleFilters.addClickListener(e -> filterSection.setVisible(!filterSection.isVisible()));

        HorizontalLayout toolbar = new HorizontalLayout(toggleFilters, addNewUser);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(new VerticalLayout(toolbar, filterSection), mainContent);
        form.setUser(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setUser(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(userClient.fetchAllUsers());
    }

    private void update() {
        String name = nameFilter.getValue();
        String email = emailFilter.getValue();

        if (!name.isEmpty()) {
            grid.setItems(userClient.fetchUsersByName(name));
        } else if (!email.isEmpty()) {
            grid.setItems(userClient.fetchUsersByEmail(email));
        } else {
            refresh();
        }
    }

    public void createUser(User user) {
        userClient.createUser(user);
        Notification.show("User created.");
        refresh();
    }

    public void updateUserByPassword(User user) {
        userClient.updateUserByPassword(user);
        Notification.show("User updated with password.");
        refresh();
    }

    public void deleteUserByPassword(String password) {
        userClient.deleteUserByPassword(password);
        Notification.show("User deleted with password.");
        refresh();
    }
}

