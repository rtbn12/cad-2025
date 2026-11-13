package com.example.demo.dto;

import java.time.LocalDate;

public class DateView {

    private final LocalDate dateSlot;

    public DateView(LocalDate dateSlot) {
        this.dateSlot = dateSlot;
    }

    public LocalDate getDateSlot() {
        return dateSlot;
    }
}
