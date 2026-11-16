package com.example.demo.repository;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatient_IdOrderBySlot_SlotStartAsc(
            Long patientId
    );

    List<Appointment> findAllBySlot_Doctor_IdOrderBySlot_SlotStartAsc(
            Long doctorId
    );

}
