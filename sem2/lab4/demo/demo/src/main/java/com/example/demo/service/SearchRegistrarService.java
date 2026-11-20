package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.exception.AppointmentNotFoundException;
import com.example.demo.exception.PatientNotFoundException;
import com.example.demo.exception.SlotIsNotInDateTimeZone;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PatientRepository;
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
    private final PatientRepository patientRepository;

    public SearchRegistrarService(SpecialityService specialityService, UserRepository userRepository,
                                  DoctorsService doctorsService, DateSlotService dateSlotService,
                                  TimeSlotService timeSlotService, AppointmentService appointmentService,
                                  AppointmentRepository appointmentRepository, PatientRepository patientRepository)
    {

        this.specialityService = specialityService;
        this.userRepository = userRepository;
        this.doctorsService = doctorsService;
        this.dateSlotService = dateSlotService;
        this.timeSlotService = timeSlotService;
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
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

    @Transactional(readOnly = true)
    public Long getSearchPatientId(String currentUserLogin, PatientSearchType patientSearchType, String searchValue){
        baseCheck(currentUserLogin);
        if (patientSearchType == null || searchValue == null || searchValue.isBlank()) {
            throw new IllegalArgumentException("Выберите способ поиска и введите значение");
        }
        searchValue = searchValue.trim();
        boolean valid = switch (patientSearchType) {
            case OMS -> searchValue.matches("[0-9]{16}");
            case PHONE -> searchValue.matches("\\+7-[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2}");
        };
        if (!valid) {
            throw new IllegalArgumentException(patientSearchType == PatientSearchType.OMS
                    ? "ОМС должен содержать ровно 16 цифр"
                    : "Введите телефон в формате +7-900-123-45-67");
        }
        Patient currentPatient;

        switch (patientSearchType){

            case PHONE ->   currentPatient = patientRepository.findByPhone(searchValue).orElseThrow(()->
                    new PatientNotFoundException("Пациент не найден"));
            case OMS -> currentPatient = patientRepository.findByOmsNumber(searchValue).orElseThrow(()->
                    new PatientNotFoundException("Пациент не найден"));
            default ->   throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Невозможно выполнить поиск без параметров");
        }
        return currentPatient.getUser().getId();
    }

    @Transactional(readOnly = true)
    public SearchPatientMiniCabinetView getPatientCabinetView(String currentUserLogin, Long currentPatientUserId){
        baseCheck(currentUserLogin);

        Patient currentPatient = patientRepository.findByUser_Id(currentPatientUserId).orElseThrow(()->
                new PatientNotFoundException("Пациент не найден"));


        List<AppointmentMiniView> myAppointments = appointmentService.getMyAppointments(
                currentPatient.getUser().getLogin());

        return new SearchPatientMiniCabinetView(currentPatient.getFirstName(),
                currentPatient.getMiddleName(), currentPatient.getLastName(), currentPatient.getOmsNumber(),
                currentPatient.getPhone(), currentPatient.getBirthDate(), currentPatient.getId(), myAppointments);
    }



    @Transactional(readOnly = true)
    public void baseCheck(String currentUserLogin){

        User currentUser = userRepository.findByLogin(currentUserLogin).orElseThrow(()->
                new UserNotFoundException("Пользователь не найден"));

        if (!currentUser.getRole().equals(UserRole.REGISTRAR)){

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Недостаточно прав");
        }
    }
}
