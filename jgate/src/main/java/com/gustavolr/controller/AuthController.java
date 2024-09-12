package com.gustavolr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.gustavolr.model.LoginRequest;
import com.gustavolr.security.JwtProvider;
import com.gustavolr.security.TokenRequest;
import com.gustavolr.service.AuthResponse;
import com.gustavolr.service.AuthenticationService;

@CrossOrigin
@RestController
public class AuthController {

    public final static String REGISTRATION_PATH = "/register";    
    public final static String LOGIN_PATH = "/auth";   
    public final static String VERIFY_TOKEN_PATH = "/verifyToken";     

    private AuthenticationService userDetailsService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    public void setAuthService(@Lazy AuthenticationService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Registers a new user with the provided username and password, assigns the "USER" role, 
     * and generates a JWT token for the user.
     *
     * @param loginRequest the {@link LoginRequest} object containing the username and password of the new user.
     * @return a {@link ResponseEntity} containing an {@link AuthResponse} with the generated JWT token if successful, 
     *         or a bad request response with an error message if registration fails.
     * @throws RuntimeException if the username is already taken or registration fails for any other reason.
     */
    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest loginRequest) {
        
        try {
            userDetailsService.registerUser(loginRequest.getUsername(), loginRequest.getPassword(), "USER");
            String token = jwtProvider.generateJwtToken(loginRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Authenticates the user with the provided username and password, and generates a JWT token if the credentials are valid.
     *
     * @param loginRequest the {@link LoginRequest} object containing the username and password of the user attempting to log in.
     * @return a {@link ResponseEntity} containing an {@link AuthResponse} with the generated JWT token if authentication succeeds, 
     *         or a bad request response with an error message if the credentials are invalid.
     */
    @PostMapping(LOGIN_PATH)
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
            if (!userDetailsService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())) 
                return ResponseEntity.badRequest().body("Bad user credentials!");

            String token = jwtProvider.generateJwtToken(loginRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Verifies the validity of the provided JWT token.
     *
     * @param tokenRequest the {@link TokenRequest} object containing the JWT token to verify.
     * @return a {@link ResponseEntity} with a success message if the token is valid, 
     *         or a bad request response with an error message if the token is invalid or expired.
     */
    @PostMapping(VERIFY_TOKEN_PATH)
    public ResponseEntity<?> verifyToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();

        if (jwtProvider.validateJwtToken(token)) {
            return ResponseEntity.ok("Token is valid!");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token!");
        }
    }
}