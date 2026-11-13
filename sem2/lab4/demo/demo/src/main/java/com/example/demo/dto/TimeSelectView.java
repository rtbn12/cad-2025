package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class TimeSelectView {

    private final Long selectDoctorId;
    private final Long selectDoctorSpecialityId;
    private final String selectSpecialityName;
    private final String selectDoctorFirstName;
    private final String selectDoctorMiddleName;
    private final String selectDoctorLastName;
    private final LocalDate selectDate;
    private final List<TimeView> timeSlots;

    public TimeSelectView(Long selectDoctorId, Long selectDoctorSpecialityId,
                          String selectSpecialityName, String selectDoctorFirstName,
                          String selectDoctorMiddleName, String selectDoctorLastName,
                          LocalDate selectDate, List<TimeView> timeSlots) {

        this.selectDoctorId = selectDoctorId;
        this.selectDoctorSpecialityId = selectDoctorSpecialityId;
        this.selectSpecialityName = selectSpecialityName;
        this.selectDoctorFirstName = selectDoctorFirstName;
        this.selectDoctorMiddleName = selectDoctorMiddleName;
        this.selectDoctorLastName = selectDoctorLastName;
        this.selectDate = selectDate;
        this.timeSlots = timeSlots;
    }

    public Long getSelectDoctorId() {
        return selectDoctorId;
    }

    public Long getSelectDoctorSpecialityId() {
        return selectDoctorSpecialityId;
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

    public LocalDate getSelectDate() {
        return selectDate;
    }

    public List<TimeView> getTimeSlots() {
        return timeSlots;
    }
}
