package com.example.demo.service;

import com.example.demo.dto.DateSelectView;
import com.example.demo.dto.DateView;
import com.example.demo.entity.Doctor;
import com.example.demo.exception.DoctorNotFoundException;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.SlotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateSlotService {

    private final SlotRepository slotRepository;
    private final DoctorRepository doctorRepository;

    public DateSlotService(SlotRepository slotRepository, DoctorRepository doctorRepository) {
        this.slotRepository = slotRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true)
    public DateSelectView getDataSelectView (Long selectDoctorId, int mode){

        Doctor doctor = doctorRepository.findById(selectDoctorId).orElseThrow(() ->
                new DoctorNotFoundException("Доктор не найден"));

        String doctorSpecialityName = doctor.getSpeciality().getName();

        Long doctorSpecialityId = doctor.getSpeciality().getId();

        List<LocalDate> availableDates;

        if(mode == 0){
            availableDates = slotRepository.findAvailableDates(selectDoctorId, LocalDateTime.now());
        }else if (mode == 1){
            availableDates = slotRepository.findDatesForRegistrar(selectDoctorId, LocalDateTime.now());
        }else{
            throw new IllegalArgumentException("Выбран недопустимый вариант загрузки");
        }

        List<DateView> dats = new ArrayList<>();

        for (LocalDate availableDate : availableDates){
            dats.add(new DateView(availableDate));
        }

        return new DateSelectView(doctorSpecialityId, doctorSpecialityName,
                doctor.getFirstName(), doctor.getMiddleName(),
                doctor.getLastName(), selectDoctorId, dats);
    }
}
