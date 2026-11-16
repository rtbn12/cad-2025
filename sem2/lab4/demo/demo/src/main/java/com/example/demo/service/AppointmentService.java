package com.example.demo.service;

import com.example.demo.dto.AppointmentDetailsView;
import com.example.demo.dto.AppointmentMiniView;
import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final SlotRepository slotRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository,
                              SlotRepository slotRepository) {

        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.slotRepository = slotRepository;
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

    @Transactional
    public void cancelAppointment(Long currentAppointmentId, String currentUserLogin){
        User currentUser = userRepository.findByLogin(currentUserLogin)
                .orElseThrow(() ->
                        new UserNotFoundException("Пользователь не найден"));

        Appointment appointment = appointmentRepository.findByIdForUpdate(currentAppointmentId).orElseThrow(()->
                new AppointmentNotFoundException("Посещение не найдено"));

        boolean allowed = switch (currentUser.getRole()) {
            case PATIENT -> appointment.getPatient().getUser().getId().equals(currentUser.getId());

            case DOCTOR -> false;

            case REGISTRAR -> true;
        };

        if (!allowed) {
            throw new AccessDeniedException(
                    "Нет доступа к этой записи на приём"
            );
        }

        Slot currentSlot = slotRepository.findByIdForUpdate(appointment.getSlot().getId()).orElseThrow(()->
                new SlotNotFoundException("Слот не найден"));
        LocalDateTime now = LocalDateTime.now();

        if(appointment.getStatus().equals(AppointmentStatus.CREATED)){

            if (!currentSlot.getSlotStart().isBefore(now.plusMinutes(10))){

                appointment.setCancelledAt(now);

                if(currentUser.getRole().equals(UserRole.PATIENT)){
                    appointment.setStatus(AppointmentStatus.CANCELLED_BY_PATIENT);
                } else if (currentUser.getRole().equals(UserRole.REGISTRAR)) {
                    appointment.setStatus(AppointmentStatus.CANCELLED_BY_REGISTRAR);
                }else{
                    throw new AccessDeniedException("Нет доступа к этой записи на приём");
                }

                currentSlot.setStatus(SlotStatus.FREE);
                slotRepository.save(currentSlot);

            }else{

                if (appointment.getSlot().getSlotStart().isAfter(now)){

                    throw  new SlotIsNotInDateTimeZone("Запись можно отменить не позднее, чем за 10 минут до её начала");

                }else{

                    throw  new SlotIsNotInDateTimeZone("Нельзя отменить запись, время начала которой уже прошло");
                }
            }

        }else{
            throw new AppointmentIsNotCreated("Заявка на посещение не активная");
        }

    }

//    @Transactional(readOnly = true)
//    public List<AppointmentMiniView> getPatientAppointments(String currentUserLogin, Long patientId){
//
//
//    }

}
