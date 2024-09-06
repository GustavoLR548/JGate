package com.gustavolr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.gustavolr.model.LoginRequest;
import com.gustavolr.model.User;
import com.gustavolr.security.JwtProvider;
import com.gustavolr.service.AuthResponse;
import com.gustavolr.service.AuthenticationService;


@RestController
public class RegistrationController {

    public final static String REGISTRATION_PATH = "/register";    
    public final static String LOGIN_PATH = "/auth";    

    private AuthenticationService userDetailsService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    public void setAuthService(@Lazy AuthenticationService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        
        try {
            userDetailsService.registerUser(user.getUsername(), user.getPassword(), user.getRole());
            String token = jwtProvider.generateJwtToken(user.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
            if (!userDetailsService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())) 
                return ResponseEntity.badRequest().body("Bad user credentials!");

            String token = jwtProvider.generateJwtToken(loginRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
    }
}