package com.gustavolr.requests;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.gustavolr.controller.AuthController;
import com.gustavolr.security.JwtProvider;
import com.gustavolr.service.AuthenticationService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Mock
    private AuthenticationService userDetailsService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    /**
     * Unit test for the registerUser function
     */
    @Test
    public void testRegisterUser_Success() throws Exception {
        String username = "testUser";
        String password = "password";
        String token = "generatedToken";

        // Mock user registration and token generation
        when(jwtProvider.generateJwtToken(anyString())).thenReturn(token);

        // Test with valid inputs
        mockMvc.perform(post(AuthController.REGISTRATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));

        verify(userDetailsService, times(1)).registerUser(username, password, "USER");
        verify(jwtProvider, times(1)).generateJwtToken(username);
    }

    @Test
    public void testRegisterUser_Failure_UsernameTaken() throws Exception {
        String username = "testUser";
        String password = "password";

        // Simulate username already taken
        doThrow(new RuntimeException("Username is already taken")).when(userDetailsService).registerUser(anyString(), anyString(), anyString());

        // Test with existing username
        mockMvc.perform(post(AuthController.REGISTRATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Username is already taken"));

        verify(userDetailsService, times(1)).registerUser(username, password, "USER");
    }

    /**
     * Unit test for the loginUser function
     */
    @Test
    public void testLoginUser_Success() throws Exception {
        String username = "testUser";
        String password = "password";
        String token = "generatedToken";

        // Mock successful authentication and token generation
        when(userDetailsService.authenticateUser(username, password)).thenReturn(true);
        when(jwtProvider.generateJwtToken(username)).thenReturn(token);

        // Test with valid credentials
        mockMvc.perform(post(AuthController.LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));

        verify(userDetailsService, times(1)).authenticateUser(username, password);
        verify(jwtProvider, times(1)).generateJwtToken(username);
    }

    @Test
    public void testLoginUser_Failure_InvalidCredentials() throws Exception {
        String username = "testUser";
        String password = "wrongPassword";

        // Simulate failed authentication
        when(userDetailsService.authenticateUser(username, password)).thenReturn(false);

        // Test with invalid credentials
        mockMvc.perform(post(AuthController.LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Bad user credentials!"));

        verify(userDetailsService, times(1)).authenticateUser(username, password);
    }

    /**
     * Unit test for the verifyToken function
     */
    @Test
    public void testVerifyToken_Success() throws Exception {
        String token = "validToken";

        // Mock successful token validation
        when(jwtProvider.validateJwtToken(token)).thenReturn(true);

        // Test with valid token
        mockMvc.perform(post(AuthController.VERIFY_TOKEN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\": \"" + token + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Token is valid!"));

        verify(jwtProvider, times(1)).validateJwtToken(token);
    }

    @Test
    public void testVerifyToken_Failure_InvalidToken() throws Exception {
        String token = "invalidToken";

        // Simulate invalid token
        when(jwtProvider.validateJwtToken(token)).thenReturn(false);

        // Test with invalid token
        mockMvc.perform(post(AuthController.VERIFY_TOKEN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\": \"" + token + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Invalid or expired token!"));

        verify(jwtProvider, times(1)).validateJwtToken(token);
    }
}