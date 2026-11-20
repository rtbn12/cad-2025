package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.exception.AppointmentNotFoundException;
import com.example.demo.exception.SlotIsNotInDateTimeZone;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchRegistrarService {

    private final SpecialityService specialityService;
    private final UserRepository userRepository;
    private final DoctorsService doctorsService;
    private final DateSlotService dateSlotService;
    private final TimeSlotService timeSlotService;
    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;


    public SearchRegistrarService(SpecialityService specialityService, UserRepository userRepository,
                                  DoctorsService doctorsService, DateSlotService dateSlotService,
                                  TimeSlotService timeSlotService, AppointmentService appointmentService,
                                  AppointmentRepository appointmentRepository) {

        this.specialityService = specialityService;
        this.userRepository = userRepository;
        this.doctorsService = doctorsService;
        this.dateSlotService = dateSlotService;
        this.timeSlotService = timeSlotService;
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    public  List<SpecialityView> getSpecialityList(String currentUserLogin){
        baseCheck(currentUserLogin);
        return  specialityService.getSpecialitiesList();
    }

    @Transactional(readOnly = true)
    public DoctorSelectionView getDoctorsView(String currentUserLogin, Long specialityId){
        baseCheck(currentUserLogin);
        return doctorsService.getDoctorSelectViews(specialityId);
    }

    @Transactional(readOnly = true)
    public DateSelectView getDates(String currentUserLogin, Long selectDoctorId){
        baseCheck(currentUserLogin);
        return dateSlotService.getDataSelectView(selectDoctorId, 1);
    }

    @Transactional(readOnly = true)
    public TimeSelectView getTimes(String currentUserLogin, Long selectDoctorId, LocalDate selectDate){
        baseCheck(currentUserLogin);
        if(selectDate.isBefore(LocalDate.now())){

            throw new SlotIsNotInDateTimeZone("Нельзя выбрать прошедшую дату");
        }
        return timeSlotService.getTimeSelectView(selectDoctorId, selectDate, 1);
    }

    @Transactional(readOnly = true)

    public AppointmentDetailsView getAppointmentView(String currentUserLogin, Long currentSlotId){
        baseCheck(currentUserLogin);

        Appointment appointment = appointmentRepository.findBySlot_IdAndStatus(currentSlotId, AppointmentStatus.CREATED)
                .orElseThrow(()-> new AppointmentNotFoundException("Посещение не найдено"));

        if(appointment.getSlot().getSlotEnd().isAfter(LocalDateTime.now())){
            return  appointmentService.getDetailView(appointment.getId(), currentUserLogin);
        }else{
            throw new SlotIsNotInDateTimeZone("Время окончания посещения уже прошло");
        }
    }





    private void baseCheck(String currentUserLogin){

        User currentUser = userRepository.findByLogin(currentUserLogin).orElseThrow(()->
                new UserNotFoundException("Пользователь не найден"));

        if (!currentUser.getRole().equals(UserRole.REGISTRAR)){

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Недостаточно прав");
        }
    }
}
