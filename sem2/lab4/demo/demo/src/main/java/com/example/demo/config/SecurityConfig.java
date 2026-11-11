package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterAccess(HttpSecurity http)throws Exception{

        http.authorizeHttpRequests(access ->
                access
                        .requestMatchers("/registration", "/registrationResult",
                                "/styles/RegistrationStyle.css", "/images/icon.ico")
                        .permitAll()
                        .anyRequest()
                        .authenticated())

                        .formLogin(Customizer.withDefaults());

        return http.build();
    }

}
