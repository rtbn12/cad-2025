package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class SearchPatientMiniCabinetView {

    private String patientFirstName;
    private String patientMiddleName;
    private String patientLastName;
    private String patientOms;
    private String patientPhone;
    private LocalDate patientBirthday;
    private Long patientId;
    private List<AppointmentMiniView> patientMiniAppointmentsView;

    public SearchPatientMiniCabinetView(String patientFirstName,
                                        String patientMiddleName, String patientLastName, String patientOms,
                                        String patientPhone, LocalDate patientBirthday, Long patientId,
                                        List<AppointmentMiniView> patientMiniAppointmentsView) {

        this.patientFirstName = patientFirstName;
        this.patientMiddleName = patientMiddleName;
        this.patientLastName = patientLastName;
        this.patientOms = patientOms;
        this.patientPhone = patientPhone;
        this.patientBirthday = patientBirthday;
        this.patientId = patientId;
        this.patientMiniAppointmentsView = patientMiniAppointmentsView;

    }

    public SearchPatientMiniCabinetView() {

    }



    public String getPatientFirstName() {
        return patientFirstName;
    }

    public String getPatientMiddleName() {
        return patientMiddleName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public String getPatientOms() {
        return patientOms;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public LocalDate getPatientBirthday() {
        return patientBirthday;
    }

    public Long getPatientId() {
        return patientId;
    }

    public List<AppointmentMiniView> getPatientMiniAppointmentsView() {
        return patientMiniAppointmentsView;
    }



    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public void setPatientMiddleName(String patientMiddleName) {
        this.patientMiddleName = patientMiddleName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public void setPatientOms(String patientOms) {
        this.patientOms = patientOms;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public void setPatientBirthday(LocalDate patientBirthday) {
        this.patientBirthday = patientBirthday;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPatientMiniAppointmentsView(List<AppointmentMiniView> patientMiniAppointmentsView) {
        this.patientMiniAppointmentsView = patientMiniAppointmentsView;
    }
}
