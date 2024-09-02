package com.gustavolr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gustavolr.model.User;
import com.gustavolr.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String username,
                                          @RequestParam String email,
                                          @RequestParam String password) {
        try {
            User user = authService.registerUser(username, email, password);
            return ResponseEntity.ok("User registered successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestParam String username,
                                              @RequestParam String password) {
        Optional<User> user = authService.authenticateUser(username, password);
        if (user.isPresent()) {
            // Aqui você pode gerar um token JWT e retorná-lo
            return ResponseEntity.ok("Authentication successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials!");
        }
    }
}