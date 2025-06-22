package me.taldir.alten.dto;

import me.taldir.alten.model.User;

public class UserResponse {

    //-------------------------
    // --- Object Variables ---
    //-------------------------
    private int id;
    private String username;
    private String firstname;
    private String email;
    private Boolean isAdmin;

    // ---------------------------
    // --- Default constructor ---
    // ---------------------------

    public UserResponse() {}

    // ------------------------------
    // --- Overloaded constructor ---
    // ------------------------------

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.email = user.getEmail();
        this.isAdmin = user.isAdmin();
    }

    // ---------------------------
    // --- Getters and Setters ---
    // ---------------------------

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }
}
