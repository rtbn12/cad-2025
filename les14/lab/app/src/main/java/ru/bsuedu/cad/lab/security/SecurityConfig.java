package ru.bsuedu.cad.lab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Пользователи в памяти (user / manager)
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password("{noop}123")
                        .roles("USER")
                        .authorities("VIEW_PROFILE")
                        .build(),
                User.withUsername("manager")
                        .password("{noop}321")
                        .roles("MANAGER")
//                        .authorities("VIEW_PROFILE", "EDIT_PROFILE", "DELETE_USERS")
                        .build()
        );
    }

    // 2. Правила доступа и форма логина
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz

                        .requestMatchers("/login", "/css/**").permitAll()

                        .requestMatchers("/web/orders").authenticated()

                        .requestMatchers("/web/create-test").hasRole("MANAGER")
                        .requestMatchers("/web/orders/edit/**").hasRole("MANAGER")
                        .requestMatchers("/web/orders/delete/**").hasRole("MANAGER")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/web/orders", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception{

        http
                .securityMatcher("/Oorders/**")
                .authorizeHttpRequests(authz -> authz

                        .requestMatchers(HttpMethod.GET, "/Oorders/**").hasAnyRole("USER", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/Oorders/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/Oorders/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/Oorders/**").hasRole("MANAGER")
                )
                .httpBasic(Customizer.withDefaults()) // включает basic auth
                .csrf(csrf -> csrf.disable());        // желательно отключить CSRF для REST API

        return http.build();
    }
}