package me.taldir.alten.dto;

import me.taldir.alten.model.User;

public class UserLoginResponse {

    //-------------------------
    // --- Object Variables ---
    //-------------------------
    private String token;
    private UserResponse user;

    // ---------------------------
    // --- Default constructor ---
    // ---------------------------

    public UserLoginResponse() {}

    // ------------------------------
    // --- Overloaded constructor ---
    // ------------------------------

    public UserLoginResponse(String token, User user) {
        this.token = token;
        this.user = new UserResponse(user);
    }

    // ---------------------------
    // --- Getters and Setters ---
    // ---------------------------
    
    public String getToken() {
        return token;
    }

    public UserResponse getUser() {
        return user;
    }
}
