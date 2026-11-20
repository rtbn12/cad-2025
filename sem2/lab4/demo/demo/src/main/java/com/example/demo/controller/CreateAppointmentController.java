package com.example.demo.controller;

import com.example.demo.dto.DateSelectView;
import com.example.demo.dto.DoctorSelectionView;
import com.example.demo.dto.SpecialityView;
import com.example.demo.dto.TimeSelectView;
import com.example.demo.service.*;
import com.example.demo.exception.DateNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyRole('PATIENT', 'REGISTRAR')")
@Controller
@RequestMapping("/cabinet/createAppointment")
public class CreateAppointmentController {

    private final SpecialityService specialityService;
    private final DoctorsService doctorsService;
    private final DateSlotService dateSlotService;
    private final TimeSlotService timeSlotService;
    private final SlotPreviewService slotPreviewService;
    private final CreateAppointmentService createAppointmentService;

    public CreateAppointmentController(SpecialityService specialityService,
                                       DoctorsService doctorsService,
                                       DateSlotService dateSlotService,
                                       TimeSlotService timeSlotService,
                                       SlotPreviewService slotPreviewService,
                                       CreateAppointmentService createAppointmentService) {

        this.specialityService = specialityService;
        this.doctorsService = doctorsService;
        this.dateSlotService = dateSlotService;
        this.timeSlotService = timeSlotService;
        this.slotPreviewService = slotPreviewService;
        this.createAppointmentService = createAppointmentService;
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

        DateSelectView dateSelectView = dateSlotService.getDataSelectView(selectDoctorId, 0);
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

        TimeSelectView timeSelectView = timeSlotService.getTimeSelectView(selectDoctorId, selectDate, 0);
        model.addAttribute("timeView", timeSelectView);

        return "selectTime";
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/confirmSlot")
    public String getPreviewSlot(@RequestParam("currentSlotId") Long currentSlotId,
                                 Authentication authentication,Model model){

        model.addAttribute("appointmentPreview",
                slotPreviewService.getAppointmentPreviewView(currentSlotId, authentication.getName()));

        return "previewSlot";
    }

    @PostMapping("/saveNewAppointment")
    public String saveNewAppointment(@RequestParam("currentSlotId") Long currentSlotId,
                                     @RequestParam("patientId") Optional<Long> patientId,
                                     Authentication authentication){

        String createdUserLogin = authentication.getName();
        createAppointmentService.createAppointment(currentSlotId, patientId, createdUserLogin);
        return "redirect:/cabinet/createAppointment/successfulCreateAppointment";
    }

    @GetMapping("/successfulCreateAppointment")
    public String goTheEnd(){

        return "resultCreateAppointment";
    }

}
