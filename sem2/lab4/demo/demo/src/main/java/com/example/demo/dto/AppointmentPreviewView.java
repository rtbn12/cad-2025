package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentPreviewView {

    private final String patientFirstName;
    private final String patientMiddleName;
    private final String patientLastName;

    private final String doctorFirstName;
    private final String doctorMiddleName;
    private final String doctorLastName;
    private final Long doctorId;

    private final String specialityName;
    private final Long specialityId;

    private final LocalDate appointmentDate;
    private final LocalTime appointStartTime;
    private final LocalTime appointmentEndTime;

    private final Long slotId;

    public AppointmentPreviewView(String patientFirstName, String patientMiddleName,
                                  String patientLastName, String doctorFirstName,
                                  String doctorMiddleName, String doctorLastName,
                                  Long doctorId, String specialityName, Long specialityId,
                                  LocalDate appointmentDate, LocalTime appointStartTime,
                                  LocalTime appointmentEndTime, Long slotId) {

        this.patientFirstName = patientFirstName;
        this.patientMiddleName = patientMiddleName;
        this.patientLastName = patientLastName;
        this.doctorFirstName = doctorFirstName;
        this.doctorMiddleName = doctorMiddleName;
        this.doctorLastName = doctorLastName;
        this.doctorId = doctorId;
        this.specialityName = specialityName;
        this.specialityId = specialityId;
        this.appointmentDate = appointmentDate;
        this.appointStartTime = appointStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.slotId = slotId;
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

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public String getDoctorMiddleName() {
        return doctorMiddleName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointStartTime() {
        return appointStartTime;
    }

    public LocalTime getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public Long getSlotId() {
        return slotId;
    }
}
