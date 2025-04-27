package com.room_reservations.views.domain.user;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserService {

    private static UserService instance;
    private Set<User> users = new HashSet<>();

    private UserService() {
        this.users = exampleData();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public Set<User> getUsers() {
        return new HashSet<>(users);
    }

    public void save(User user) {
        users.add(user);
    }

    public void delete(User user) {
        users.remove(user);
    }

    public Set<User> findByNameOrEmail(String name, String email) {
        return users.stream()
                .filter(user -> (name == null || user.getName().toLowerCase().contains(name.toLowerCase()))
                        && (email == null || user.getEmail().toLowerCase().contains(email.toLowerCase())))
                .collect(Collectors.toSet());
    }

    private Set<User> exampleData() {
        Set<User> exampleUsers = new HashSet<>();
        exampleUsers.add(new User("Piotr Nowak", "pnowak@example.com", 100));
        exampleUsers.add(new User("Andrzej Wiśniewski", "awisniewski@example.com", 80));
        exampleUsers.add(new User("Anna Kowalska", "akowalska@example.com", 60));
        return exampleUsers;
    }
}
