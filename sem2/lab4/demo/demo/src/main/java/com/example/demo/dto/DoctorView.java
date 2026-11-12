package com.example.demo.dto;

public class DoctorView {

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final Long doctorId;

    public DoctorView(String firstName, String middleName, String lastName, Long doctorId) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

}
