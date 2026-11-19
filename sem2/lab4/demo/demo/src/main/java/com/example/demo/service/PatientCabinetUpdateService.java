package com.example.demo.service;

import com.example.demo.dto.CabinetView;
import com.example.demo.dto.PatientProfileUpdateFormDto;
import com.example.demo.dto.PatientRegistrationFormDto;
import com.example.demo.entity.Patient;
import com.example.demo.entity.User;
import com.example.demo.exception.PatientNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientCabinetUpdateService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final CabinetService cabinetService;
    private final PasswordEncoder passwordEncoder;

    public PatientCabinetUpdateService(UserRepository userRepository,
                                       PatientRepository patientRepository,
                                       CabinetService cabinetService,
                                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.cabinetService = cabinetService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public PatientProfileUpdateFormDto getPatientProfileForUpdate(String currentUserLogin){

       User currentUser = userRepository.findByLogin(currentUserLogin).orElseThrow(()->
               new UserNotFoundException("Пользователь не найден"));

       Patient currentPatient = patientRepository.findByUser_Id(currentUser.getId()).orElseThrow(()->
               new PatientNotFoundException("Пациент не найден"));

        return new PatientProfileUpdateFormDto(currentPatient.getFirstName(), currentPatient.getMiddleName(),
                currentPatient.getLastName(), currentPatient.getId(), currentPatient.getPhone(),
                currentPatient.getOmsNumber(), "", "",
                currentPatient.getBirthDate());

    }

    @Transactional
    public PatientRegistrationResult updateUserProfile(String currentUserLogin,
                                                       PatientProfileUpdateFormDto newPatientDTO){

        User currentUser = userRepository.findByLoginForUpdate(currentUserLogin).orElseThrow(()->
                new UserNotFoundException("Пользователь не найден"));

        Patient currentPatient = patientRepository.findByUser_IdForUpdate(currentUser.getId()).orElseThrow(()->
                new PatientNotFoundException("Пациент не найден"));

        if(patientRepository.existsByPhoneAndIdNot(newPatientDTO.getPatientPhone(), currentPatient.getId())){
            return PatientRegistrationResult.PHONE_TAKEN;
        }else{

            if(patientRepository.existsByOmsNumberAndIdNot(newPatientDTO.getPatientOmsNumber(), currentPatient.getId())){
                return  PatientRegistrationResult.OMS_TAKEN;
            }else{

                if (newPatientDTO.isChangePassword() && newPatientDTO.getPatientNewPassword() != null
                        && !newPatientDTO.getPatientNewPassword().isBlank()) {
                    currentUser.setPasswordHash(passwordEncoder.encode(newPatientDTO.getPatientNewPassword()));
                }

                currentPatient.setBirthDate(newPatientDTO.getPatientBirthday());
                currentPatient.setPhone(newPatientDTO.getPatientPhone());
                currentPatient.setFirstName(newPatientDTO.getPatientFirstName());
                currentPatient.setMiddleName(newPatientDTO.getPatientMiddleName());
                currentPatient.setLastName(newPatientDTO.getPatientLastName());
                currentPatient.setOmsNumber(newPatientDTO.getPatientOmsNumber());

                userRepository.save(currentUser);
                patientRepository.save(currentPatient);

                return PatientRegistrationResult.SUCCESS;
            }
        }

    }
}
