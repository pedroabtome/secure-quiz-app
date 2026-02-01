package com.pedroabtome.secure_quiz_app.model;

public class User {

    private String username;
    private String email;
    private String password;
    private String role;

    // Constructors
    public User() {
    }

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        // IMPORTANT: do not include password in toString() (it may end up in logs)
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
