package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "slots", schema = "public",
        uniqueConstraints = @UniqueConstraint(name = "uq_slot_doctor_start", columnNames = {"doctor_id", "slot_start"}))
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slots_id_generator")
    @SequenceGenerator(name = "slots_id_generator", sequenceName = "slots_id_seq", schema = "public", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "slot_start", nullable = false)
    private LocalDateTime slotStart;

    @Column(name = "slot_end", nullable = false)
    private LocalDateTime slotEnd;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false, columnDefinition = "slot_status")
    private SlotStatus status = SlotStatus.FREE;

    @OneToMany(mappedBy = "slot")
    private List<Appointment> appointments = new ArrayList<>();

    public Slot() {
    }

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getSlotStart() {
        return slotStart;
    }

    public void setSlotStart(LocalDateTime slotStart) {
        this.slotStart = slotStart;
    }

    public LocalDateTime getSlotEnd() {
        return slotEnd;
    }

    public void setSlotEnd(LocalDateTime slotEnd) {
        this.slotEnd = slotEnd;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

}
