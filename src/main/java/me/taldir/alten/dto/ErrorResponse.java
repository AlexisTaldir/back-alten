package me.taldir.alten.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ErrorResponse {

    //-------------------------
    // --- Object Variables ---
    //-------------------------

    @NotBlank(message = "Error message is required")
    @Size(min = 2, max = 255, message = "Error message must be between 2 and 255 characters")
    private String message;

    // ---------------------------
    // --- Default constructor ---
    // ---------------------------
    
    public ErrorResponse() {}

    // ------------------------------
    // --- Overloaded constructor ---
    // ------------------------------

    public ErrorResponse(String message) {
        this.message = message;
    }

    // ---------------------------
    // --- Getters and Setters ---
    // ---------------------------
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
