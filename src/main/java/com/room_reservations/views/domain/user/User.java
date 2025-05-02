package com.room_reservations.views.domain.user;

public class User {

    private String name;
    private String email;
    private int points;
    private String password;

    public User() {}

    public User(String name, String email, int points, String password) {
        this.name = name;
        this.email = email;
        this.points = points;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordMatches(String enteredPassword) {
        return this.password != null && this.password.equals(enteredPassword);
    }
}
