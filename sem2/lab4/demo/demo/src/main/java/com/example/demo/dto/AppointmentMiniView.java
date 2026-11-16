package com.example.demo.dto;

import com.example.demo.entity.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentMiniView {

    private final LocalDate date;
    private final LocalTime startAppointment;
    private final LocalTime endAppointment;
    private final String specialityName;
    private final String doctorFirstName;
    private final String doctorMiddleName;
    private final String doctorLastName;
    private final String patientFirstName;
    private final String patientMiddleName;
    private final String patientLastName;
    private final Long appointmentId;
    private final AppointmentStatus status;

    public AppointmentMiniView(LocalDate date, LocalTime startAppointment,
                               LocalTime endAppointment, String specialityName,
                               String doctorFirstName, String doctorMiddleName,
                               String doctorLastName, String patientFirstName,
                               String patientMiddleName, String patientLastName,
                               Long appointmentId, AppointmentStatus status) {
        this.date = date;
        this.startAppointment = startAppointment;
        this.endAppointment = endAppointment;
        this.specialityName = specialityName;
        this.doctorFirstName = doctorFirstName;
        this.doctorMiddleName = doctorMiddleName;
        this.doctorLastName = doctorLastName;
        this.patientFirstName = patientFirstName;
        this.patientMiddleName = patientMiddleName;
        this.patientLastName = patientLastName;
        this.appointmentId = appointmentId;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartAppointment() {
        return startAppointment;
    }

    public LocalTime getEndAppointment() {
        return endAppointment;
    }

    public String getSpecialityName() {
        return specialityName;
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

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public String getPatientMiddleName() {
        return patientMiddleName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
