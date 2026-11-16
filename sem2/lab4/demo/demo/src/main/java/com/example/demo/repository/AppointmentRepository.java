package com.example.demo.repository;

import com.example.demo.entity.Appointment;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatient_IdOrderBySlot_SlotStartAsc(
            Long patientId
    );

    List<Appointment> findAllBySlot_Doctor_IdOrderBySlot_SlotStartAsc(
            Long doctorId
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Appointment s WHERE s.id = :id")
    Optional<Appointment> findByIdForUpdate(@Param("id") Long id);

}
