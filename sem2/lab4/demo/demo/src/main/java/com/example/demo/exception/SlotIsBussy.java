package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SlotIsBussy extends RuntimeException {
    public SlotIsBussy(String message) {
        super(message);
    }
}
