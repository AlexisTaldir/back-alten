package me.taldir.alten.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginRequest {
    //-------------------------
    // --- Object Variables ---
    //-------------------------

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    // ---------------------------
    // --- Default constructor ---
    // ---------------------------

    public UserLoginRequest() {}

    // ------------------------------
    // --- Overloaded constructor ---
    // ------------------------------

    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // ---------------------------
    // --- Getters and Setters ---
    // ---------------------------

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
