package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails patient = User.withUsername("patient")
                .password(passwordEncoder.encode("patient"))
                .roles("PATIENT")
                .build();

        UserDetails doctor = User.withUsername("doctor")
                .password(passwordEncoder.encode("doctor"))
                .roles("DOCTOR")
                .build();

        UserDetails registrar = User.withUsername("registrator")
                .password(passwordEncoder.encode("registrator"))
                .roles("REGISTRAR")
                .build();

        return new InMemoryUserDetailsManager(patient, doctor, registrar);
    }
}
