package com.example.demo.service;

import com.example.demo.dto.TimeSelectView;
import com.example.demo.dto.TimeView;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Slot;
import com.example.demo.entity.SlotStatus;
import com.example.demo.exception.DoctorNotFoundException;
import com.example.demo.exception.DateNotFoundException;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.SlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotService {

    private final SlotRepository slotRepository;
    private final DoctorRepository doctorRepository;

    public TimeSlotService(SlotRepository slotRepository,
                           DoctorRepository doctorRepository) {
        this.slotRepository = slotRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true)
    public TimeSelectView getTimeSelectView(Long selectDoctorId, LocalDate selectDate) {

        Doctor doctor = doctorRepository.findById(selectDoctorId).orElseThrow(() ->
                new DoctorNotFoundException("Доктор не найден"));


        LocalDateTime dayStart = selectDate.atStartOfDay();
        LocalDateTime nextDayStart = selectDate.plusDays(1).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        List<Slot> slots = slotRepository.findSlotsForDay(
                selectDoctorId, dayStart, nextDayStart);
        if (slots.isEmpty()) {
            throw new DateNotFoundException("У врача нет расписания на выбранную дату");
        }
        List<TimeView> timeViews = new ArrayList<>();

        for (Slot slot : slots) {

            boolean available = slot.getStatus() == SlotStatus.FREE
                    && slot.getSlotStart().isAfter(now);

            timeViews.add(new TimeView(slot.getId(),
                    slot.getSlotStart().toLocalTime(),
                    slot.getSlotEnd().toLocalTime(),
                    slot.getStatus(), available));

        }

        return new TimeSelectView(doctor.getId(),doctor.getSpeciality().getId(),
                doctor.getSpeciality().getName(), doctor.getFirstName(),
                doctor.getMiddleName(), doctor.getLastName(), selectDate, timeViews);
    }
}
