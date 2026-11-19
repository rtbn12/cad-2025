package com.example.demo.repository;

import com.example.demo.entity.Patient;
import com.example.demo.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUser_Id(Long userId);

    boolean existsByOmsNumber(String omsNumber);

    boolean existsByPhone(String phone);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Patient p WHERE p.user.id = :id")
    Optional<Patient> findByUser_IdForUpdate(@Param("id") Long id);

    boolean existsByPhoneAndIdNot(String phone, Long patientId);

    boolean existsByOmsNumberAndIdNot(String omsNumber, Long patientId);

}
