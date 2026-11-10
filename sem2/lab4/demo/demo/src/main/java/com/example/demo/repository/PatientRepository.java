package com.example.demo.repository;

import com.example.demo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUser_Id(Long userId);

}
