package com.example.demo.dto;

import java.util.List;

public class DateSelectView {

    private final Long selectSpecialityId;
    private final String selectSpecialityName;
    private final String selectDoctorFirstName;
    private final String selectDoctorMiddleName;
    private final String selectDoctorLastName;
    private final Long doctorId;
    private final List<DateView> dataViews;

    public DateSelectView(Long selectSpecialityId, String selectSpecialityName,
                          String selectDoctorFirstName, String selectDoctorMiddleName,
                          String selectDoctorLastName, Long doctorId, List<DateView> dataViews) {

        this.selectSpecialityId = selectSpecialityId;
        this.selectSpecialityName = selectSpecialityName;
        this.selectDoctorFirstName = selectDoctorFirstName;
        this.selectDoctorMiddleName = selectDoctorMiddleName;
        this.selectDoctorLastName = selectDoctorLastName;
        this.doctorId = doctorId;
        this.dataViews = dataViews;
    }

    public Long getSelectSpecialityId() {
        return selectSpecialityId;
    }

    public String getSelectSpecialityName() {
        return selectSpecialityName;
    }

    public String getSelectDoctorFirstName() {
        return selectDoctorFirstName;
    }

    public String getSelectDoctorMiddleName() {
        return selectDoctorMiddleName;
    }

    public String getSelectDoctorLastName() {
        return selectDoctorLastName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public List<DateView> getDataViews() {
        return dataViews;
    }
}
