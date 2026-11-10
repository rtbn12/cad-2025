package com.example.demo.service;

import com.example.demo.dto.CabinetView;
import com.example.demo.entity.*;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.RegistrarRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CabinetService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final RegistrarRepository registrarRepository;
    private final UserRepository userRepository;



    public CabinetService(DoctorRepository doctorRepository, PatientRepository patientRepository,
                          RegistrarRepository registrarRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.registrarRepository = registrarRepository;
        this.userRepository = userRepository;
    }

    public CabinetView generateCabinetDto (String login){

        UserRole role;
        LocalDateTime createdAt;
        String firstName;
        String middleName;
        String lastName;

        User user = userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден"));

        createdAt = user.getCreatedAt();
        role = user.getRole();

        Long user_id = user.getId();

        switch (role){

            case PATIENT -> {
                Patient patient = patientRepository.findByUser_Id(user_id).orElseThrow(() ->
                        new UsernameNotFoundException("Пациент не найден"));

                firstName = patient.getFirstName();
                middleName = patient.getMiddleName();
                lastName = patient.getLastName();
                return new CabinetView(login, role, createdAt,firstName, middleName, lastName);
            }

            case DOCTOR ->  {
                Doctor doctor = doctorRepository.findByUser_Id(user_id).orElseThrow(() ->
                        new UsernameNotFoundException("Доктор не найден"));

                firstName = doctor.getFirstName();
                middleName = doctor.getMiddleName();
                lastName = doctor.getLastName();
                return new CabinetView(login, role, createdAt,firstName, middleName, lastName);
            }

            case REGISTRAR -> {
                Registrar registrar = registrarRepository.findByUser_Id(user_id).orElseThrow(() ->
                        new UsernameNotFoundException("Сотрудник регистратуры не найден"));

                firstName = registrar.getFirstName();
                middleName = registrar.getMiddleName();
                lastName = registrar.getLastName();
                return new CabinetView(login, role, createdAt,firstName, middleName, lastName);
            }
            default -> throw new IllegalStateException("Неподдерживаемая роль: " + role);
        }
    }
}
