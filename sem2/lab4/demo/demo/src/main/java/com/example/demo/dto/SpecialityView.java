package com.example.demo.dto;

public class SpecialityView {

    private final Long id;
    private final String specialityName;

    public SpecialityView(Long id, String specialityName) {
        this.id = id;
        this.specialityName = specialityName;
    }

    public Long getId() {
        return id;
    }

    public String getSpecialityName() {
        return specialityName;
    }
}
