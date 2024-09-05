package com.gustavolr.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gustavolr.model.User;
import com.gustavolr.repository.UserRepository;


@Service
public class JGateUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

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
            throw new UsernameNotFoundException("no user");
        }
        
    }
    
    private String[] getRoles(User user) {
        if (user.getRole() == null) {
            return new String[]{"USER"};
        }

        return user.getRole().split(",");
    }
}