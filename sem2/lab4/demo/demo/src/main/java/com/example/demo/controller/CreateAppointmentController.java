package com.example.demo.controller;

import com.example.demo.dto.DateSelectView;
import com.example.demo.dto.DoctorSelectionView;
import com.example.demo.dto.SpecialityView;
import com.example.demo.dto.TimeSelectView;
import com.example.demo.service.DateSlotService;
import com.example.demo.service.DoctorsService;
import com.example.demo.service.SpecialityService;
import com.example.demo.service.TimeSlotService;
import com.example.demo.exception.DateNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@PreAuthorize("hasAnyRole('PATIENT', 'REGISTRAR')")
@Controller
@RequestMapping("/cabinet/createAppointment")
public class CreateAppointmentController {

    private final SpecialityService specialityService;
    private final DoctorsService doctorsService;
    private final DateSlotService dateSlotService;
    private final TimeSlotService timeSlotService;

    public CreateAppointmentController(SpecialityService specialityService, DoctorsService doctorsService,
                                       DateSlotService dateSlotService, TimeSlotService timeSlotService) {
        this.specialityService = specialityService;
        this.doctorsService = doctorsService;
        this.dateSlotService = dateSlotService;
        this.timeSlotService = timeSlotService;
    }

    @GetMapping("/setSpeciality")
    public String getSpecialities (Model model){

        List<SpecialityView> specialityViews = specialityService.getSpecialitiesList();
        model.addAttribute("listDto", specialityViews);

        return "selectSpeciality";
    }

    @GetMapping("/setDoctor")
    public String getDoctors (@RequestParam("specialityId") Long specialityId, Model model){

        DoctorSelectionView doctorsSelectionViews = doctorsService.getDoctorSelectViews(specialityId);
        model.addAttribute("doctorsViews",doctorsSelectionViews);

        return "selectDoctor";


    }

    @GetMapping("/setDate")
    public String getDats(@RequestParam("selectDoctorId") Long selectDoctorId, Model model){

        DateSelectView dateSelectView = dateSlotService.getDataSelectView(selectDoctorId);
        model.addAttribute("dateView", dateSelectView);

        return "selectDate";
    }

    @GetMapping("/setTime")
    public String getTimes(@RequestParam("selectDoctorId") Long selectDoctorId,
                           @RequestParam("selectDate") String dateValue,
                           Model model){

        LocalDate selectDate;
        try {
            selectDate = LocalDate.parse(dateValue);
        } catch (DateTimeParseException exception) {
            throw new DateNotFoundException("Некорректная дата посещения");
        }
        if (selectDate.getYear() < 1 || selectDate.getYear() > 9999) {
            throw new DateNotFoundException("Дата посещения вне допустимого диапазона");
        }

        TimeSelectView timeSelectView = timeSlotService.getTimeSelectView(selectDoctorId, selectDate);
        model.addAttribute("timeView", timeSelectView);

        return "selectTime";
    }

}
