package com.example.demo.service;

import com.example.demo.dto.PatientRegistrationFormDto;
import com.example.demo.entity.Patient;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PatientRegistrationService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientRegistrationService(UserRepository userRepository,
                                      PatientRepository patientRepository,
                                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public  PatientRegistrationResult savePatient(PatientRegistrationFormDto patientDto){

        if (userRepository.existsByLogin(patientDto.getLogin())){

            return PatientRegistrationResult.LOGIN_TAKEN;

        }else{

            if(patientRepository.existsByOmsNumber(patientDto.getOmsNumber())){

                return PatientRegistrationResult.OMS_TAKEN;

            }else{

                User currentUser = new User();

                currentUser.setRole(UserRole.PATIENT);
                currentUser.setLogin(patientDto.getLogin());
                currentUser.setPasswordHash(
                        passwordEncoder.encode(patientDto.getPassword()));
                currentUser.setCreatedAt(LocalDateTime.now());

                userRepository.save(currentUser);

                Patient currentPatient = new Patient();

                currentPatient.setFirstName(patientDto.getFirstName());
                currentPatient.setMiddleName(patientDto.getMiddleName());
                currentPatient.setLastName(patientDto.getLastName());
                currentPatient.setUser(currentUser);
                currentPatient.setBirthDate(patientDto.getBirthDate());
                currentPatient.setPhone(patientDto.getPhone());
                currentPatient.setOmsNumber(patientDto.getOmsNumber());

                patientRepository.save(currentPatient);

                return PatientRegistrationResult.SUCCESS;

            }


        }

    }
}
