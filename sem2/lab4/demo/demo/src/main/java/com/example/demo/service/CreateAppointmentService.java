package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.exception.*;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.SlotRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CreateAppointmentService {

    private final SlotRepository slotRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    public CreateAppointmentService(SlotRepository slotRepository,
                                    AppointmentRepository appointmentRepository,
                                    UserRepository userRepository,
                                    PatientRepository patientRepository) {

        this.slotRepository = slotRepository;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional(timeout = 30)
    public Appointment createAppointment(Long slotId, Optional<Long> patientId, String createdUserLogin) {

        Slot slot = slotRepository.findByIdForUpdate(slotId).orElseThrow(()->
                new SlotNotFoundException("Слот не найден"));

        if (!slot.getStatus().equals(SlotStatus.FREE)) {
            throw new SlotIsBussy("Слот уже занят");
        }

        if (!slot.getSlotStart().isAfter(LocalDateTime.now())) {
            throw new SlotIsNotInDateTimeZone(
                    "Время начала приёма уже наступило"
            );
        }


        User createdUser = userRepository.findByLogin(createdUserLogin).orElseThrow(()->
                new UserNotFoundException("Пользователь не найден"));

        Patient patient;

        if(createdUser.getRole().equals(UserRole.PATIENT)){

             patient = patientRepository.findByUser_Id(createdUser.getId()).orElseThrow(()->
                    new PatientNotFoundException("Пациент не найден"));

        } else if (createdUser.getRole().equals(UserRole.REGISTRAR)) {
            Long selectedPatientId = patientId.orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Для записи через регистратора необходимо выбрать пациента"
                    ));

            patient = patientRepository.findById(selectedPatientId).orElseThrow(() ->
                    new PatientNotFoundException("Пациент не найден"));
        }else{

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Недостаточно прав"
            );

        }



        slot.setStatus(SlotStatus.BOOKED);


        Appointment appointment = new Appointment();
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setCreatedBy(createdUser);
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.CREATED);
        appointment.setSlot(slot);

        return appointmentRepository.save(appointment);
    }

}
