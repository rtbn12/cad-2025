package com.example.demo.service;

import com.example.demo.dto.AppointmentPreviewView;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Slot;
import com.example.demo.entity.SlotStatus;
import com.example.demo.entity.User;
import com.example.demo.exception.*;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.SlotRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class SlotPreviewService {

    private final SlotRepository slotRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;


    public SlotPreviewService(SlotRepository slotRepository,
                              UserRepository userRepository,
                              PatientRepository patientRepository) {

        this.slotRepository = slotRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public AppointmentPreviewView getAppointmentPreviewView(Long currentSlotId, String currentPatientLogin){

        Slot currentSlot = slotRepository.findById(currentSlotId).orElseThrow(()->
                new SlotNotFoundException("Слот не найден"));

        if(!currentSlot.getStatus().equals(SlotStatus.FREE)){

            throw new SlotIsBussy("Данный временной слот уже занят другим пользователем");
        }

        LocalDate currentDate = currentSlot.getSlotStart().toLocalDate();
        LocalTime currentStartTime = currentSlot.getSlotStart().toLocalTime();
        LocalTime currentEndTime = currentSlot.getSlotEnd().toLocalTime();

        if (!currentSlot.getSlotStart().isAfter(LocalDateTime.now())) {
            throw new SlotIsNotInDateTimeZone(
                    "Время начала приёма уже наступило"
            );
        }

        Long currentDoctorId = currentSlot.getDoctor().getId();
        Long currentSpecialityId = currentSlot.getDoctor().getSpeciality().getId();

        String currentDoctorFirstName = currentSlot.getDoctor().getFirstName();
        String currentDoctorMiddleName = currentSlot.getDoctor().getMiddleName();
        String currentDoctorLastName = currentSlot.getDoctor().getLastName();

        String currentSpecialityName = currentSlot.getDoctor().getSpeciality().getName();

        User currentUser = userRepository.findByLogin(currentPatientLogin).orElseThrow(()->
                new UserNotFoundException("Пользователь не найден"));

        Patient currentPatient = patientRepository.findByUser_Id(currentUser.getId()).orElseThrow(()->
                new NotFoundPatientException("Пациент не найден"));

        String currentPatientFirstName = currentPatient.getFirstName();
        String currentPatientMiddleName = currentPatient.getMiddleName();
        String currentPatientLastName = currentPatient.getLastName();

        return new AppointmentPreviewView(currentPatientFirstName,currentPatientMiddleName,
                currentPatientLastName, currentDoctorFirstName, currentDoctorMiddleName,
                currentDoctorLastName, currentDoctorId, currentSpecialityName,
                currentSpecialityId, currentDate, currentStartTime,
                currentEndTime, currentSlotId);

    }
}
