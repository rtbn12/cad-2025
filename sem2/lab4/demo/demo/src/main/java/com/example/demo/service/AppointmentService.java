package com.example.demo.service;

import com.example.demo.dto.AppointmentDetailsView;
import com.example.demo.dto.AppointmentMiniView;
import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {

        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true)
    public List<AppointmentMiniView> getMyAppointments(String currentUserLogin){

        User currentUser = userRepository.findByLogin(currentUserLogin).orElseThrow(()->
                new UserNotFoundException("Пользователь не найден"));

        List<Appointment> appointmentsList;

        if(currentUser.getRole().equals(UserRole.PATIENT)){

            Patient currentPatient = patientRepository.findByUser_Id(currentUser.getId()).orElseThrow(()->
                    new PatientNotFoundException("Пациент не найден"));

            appointmentsList = appointmentRepository.
                    findAllByPatient_IdOrderBySlot_SlotStartAsc(currentPatient.getId());

        } else if (currentUser.getRole().equals(UserRole.DOCTOR)) {

            Doctor currentDoctor = doctorRepository.findByUser_Id(currentUser.getId()).orElseThrow(()->
                    new DoctorNotFoundException("Врач не найден"));

            appointmentsList = appointmentRepository.
                    findAllBySlot_Doctor_IdOrderBySlot_SlotStartAsc(currentDoctor.getId());

        }else{
            throw new AccessDeniedException("Нет прав доступа");
        }

        List<AppointmentMiniView> miniList = new ArrayList<>();

        for(Appointment appointment: appointmentsList){

            miniList.add(new AppointmentMiniView(appointment.getSlot().getSlotStart().toLocalDate(),
                    appointment.getSlot().getSlotStart().toLocalTime(),
                    appointment.getSlot().getSlotEnd().toLocalTime(),
                    appointment.getSlot().getDoctor().getSpeciality().getName(),
                    appointment.getSlot().getDoctor().getFirstName(),
                    appointment.getSlot().getDoctor().getMiddleName(),
                    appointment.getSlot().getDoctor().getLastName(),
                    appointment.getPatient().getFirstName(),
                    appointment.getPatient().getMiddleName(),
                    appointment.getPatient().getLastName(),
                    appointment.getId(),
                    appointment.getStatus()));

        }
        return miniList;
    }

    @Transactional(readOnly = true)
    public AppointmentDetailsView getDetailView(Long currentAppointmentId, String currentUserLogin){

        User currentUser = userRepository.findByLogin(currentUserLogin)
                .orElseThrow(() ->
                        new UserNotFoundException("Пользователь не найден"));

        Appointment appointment = appointmentRepository.findById(currentAppointmentId).orElseThrow(()->
                new AppointmentNotFoundException("Посещение не найдено"));


        boolean allowed = switch (currentUser.getRole()) {
            case PATIENT -> appointment.getPatient().getUser().getId().equals(currentUser.getId());

            case DOCTOR -> appointment.getSlot().getDoctor().getUser().getId().equals(currentUser.getId());

            case REGISTRAR -> true;
        };

        if (!allowed) {
            throw new AccessDeniedException(
                    "Нет доступа к этой записи на приём"
            );
        }

        AppointmentMiniView currentMiniView = new AppointmentMiniView(appointment.getSlot().getSlotStart().toLocalDate(),
                appointment.getSlot().getSlotStart().toLocalTime(),
                appointment.getSlot().getSlotEnd().toLocalTime(),
                appointment.getSlot().getDoctor().getSpeciality().getName(),
                appointment.getSlot().getDoctor().getFirstName(),
                appointment.getSlot().getDoctor().getMiddleName(),
                appointment.getSlot().getDoctor().getLastName(),
                appointment.getPatient().getFirstName(),
                appointment.getPatient().getMiddleName(),
                appointment.getPatient().getLastName(),
                appointment.getId(),
                appointment.getStatus());

        return new AppointmentDetailsView(currentMiniView, appointment.getPatient().getId(),
                appointment.getSlot().getDoctor().getId(),
                appointment.getSlot().getDoctor().getSpeciality().getId(),
                appointment.getSlot().getId(),
                appointment.getCreatedAt(),
                appointment.getCancelledAt(),
                appointment.getCreatedBy().getRole());

    }

//    @Transactional(readOnly = true)
//    public List<AppointmentMiniView> getPatientAppointments(String currentUserLogin, Long patientId){
//
//
//    }

}
