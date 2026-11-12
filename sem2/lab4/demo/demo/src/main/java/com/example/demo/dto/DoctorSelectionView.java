package com.example.demo.dto;

import java.util.List;

public class DoctorSelectionView {

    private final String specialityName;
    private final Long specialityId;
    private final List<DoctorView> doctors;

    public DoctorSelectionView(String specialityName, Long specialityId,
                               List<DoctorView> doctors) {
        this.specialityName = specialityName;
        this.specialityId = specialityId;
        this.doctors = doctors;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public List<DoctorView> getDoctors() {
        return doctors;
    }
}
