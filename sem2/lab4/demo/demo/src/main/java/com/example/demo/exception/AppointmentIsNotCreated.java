package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AppointmentIsNotCreated extends RuntimeException {
    public AppointmentIsNotCreated(String message) {
        super(message);
    }
}
