package me.taldir.alten.controller;

import me.taldir.alten.model.User;
import me.taldir.alten.repository.UserRepository;
import me.taldir.alten.security.JwtService;
import me.taldir.alten.dto.UserRegisterRequest;
import me.taldir.alten.dto.UserResponse;
import me.taldir.alten.dto.UserLoginRequest;
import me.taldir.alten.dto.ErrorResponse;
import me.taldir.alten.dto.UserLoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Authentication", description = "Operations related to user authentication and registration")
public class AuthController {
    @Autowired private UserRepository userRepo;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @io.swagger.v3.oas.annotations.Operation(summary = "Register a new user", description = "Registers a new user with the provided details. Returns the created user object.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User registered successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Email already used")
        }
    )
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Email already used"));
        }
        User user = new User(req.getUsername(), req.getFirstname(), req.getEmail(), passwordEncoder.encode(req.getPassword()), false);
        userRepo.save(user);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.Operation(summary = "User login", description = "Authenticates a user and returns a JWT token and user information if successful.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid credentials")
        }
    )
    public ResponseEntity<?> login(@RequestBody UserLoginRequest req) {
        var user = userRepo.findByEmail(req.getEmail());
        if (user.isPresent() && passwordEncoder.matches(req.getPassword(), user.get().getPassword())) {
            String token = jwtService.generateToken(user.get().getEmail(), user.get().getId());
            return ResponseEntity.ok(new UserLoginResponse(token, user.get()));
        }
        return ResponseEntity.status(401).body("User not found or invalid credentials");
    }
}
