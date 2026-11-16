package com.example.demo.dto;

import com.example.demo.entity.UserRole;

import java.time.LocalDateTime;

public class AppointmentDetailsView {

    private final AppointmentMiniView appointment;

    private final Long patientId;
    private final Long doctorId;
    private final Long specialityId;
    private final Long slotId;

    private final LocalDateTime createdAt;
    private final LocalDateTime cancelledAt;
    private final UserRole createdByRole;

    public AppointmentDetailsView(
            AppointmentMiniView appointment,
            Long patientId,
            Long doctorId,
            Long specialityId,
            Long slotId,
            LocalDateTime createdAt,
            LocalDateTime cancelledAt,
            UserRole createdByRole
    ) {
        this.appointment = appointment;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.specialityId = specialityId;
        this.slotId = slotId;
        this.createdAt = createdAt;
        this.cancelledAt = cancelledAt;
        this.createdByRole = createdByRole;
    }

    public AppointmentMiniView getAppointment() {
        return appointment;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public Long getSlotId() {
        return slotId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public UserRole getCreatedByRole() {
        return createdByRole;
    }
}