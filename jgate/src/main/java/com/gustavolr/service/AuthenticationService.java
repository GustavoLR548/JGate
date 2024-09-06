package com.gustavolr.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gustavolr.model.User;
import com.gustavolr.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            User userObj = user.get();
            return org.springframework.security.core.userdetails.User.withUsername(userObj.getUsername())
                .password(userObj.getPassword())
                .roles(getRoles(userObj))
                .build();
        } else {
            throw new UsernameNotFoundException("no user found, or credentials are incorrect");
        }
        
    }
    
    private String[] getRoles(User user) {
        if (user.getRole() == null) {
            return new String[]{"USER"};
        }

        return user.getRole().split(",");
    }


    public boolean authenticateUser(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            return passwordEncoder.matches(rawPassword, user.getPassword());
        }

        return false; 
    }
}