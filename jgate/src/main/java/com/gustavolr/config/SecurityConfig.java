package com.gustavolr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.gustavolr.model.RoleName;
import com.gustavolr.service.JGateUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JGateUserDetailService userDetailsService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> {
            authz.requestMatchers("/index", "/register/**").permitAll();  // Allow access to public and login endpoints
            authz.requestMatchers("/admin/**").hasRole(RoleName.ADMIN.toString());  // Allow access to public and login endpoints
            authz.requestMatchers("/user/**").hasRole(RoleName.USER.toString());  // Allow access to public and login endpoints
            authz.anyRequest().authenticated();
        })
        .formLogin(form -> {
            form.loginPage("/login").permitAll();
        })
        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar BCrypt para codificação de senhas
    }
    
    // Define a UserDetailsService bean for in-memory authentication
    //@Bean
    //public UserDetailsService userDetailsService() {
    //    UserDetails user = User.withUsername("user")
    //           .password(passwordEncoder().encode("password"))
    //            .roles("USER")
    //            .build();
    //
    //    UserDetails admin = User.withUsername("admin")
    //            .password(passwordEncoder().encode("admin123"))
    //            .roles("ADMIN")
    //            .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}