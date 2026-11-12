package com.example.demo.controller;

import com.example.demo.dto.DoctorSelectionView;
import com.example.demo.dto.SpecialityView;
import com.example.demo.service.DoctorsService;
import com.example.demo.service.SpecialityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@PreAuthorize("hasAnyRole('PATIENT', 'REGISTRAR')")
@Controller
@RequestMapping("/cabinet/createAppointment")
public class CreateAppointmentController {

    private final SpecialityService specialityService;
    private final DoctorsService doctorsService;

    public CreateAppointmentController(SpecialityService specialityService, DoctorsService doctorsService) {
        this.specialityService = specialityService;
        this.doctorsService = doctorsService;
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

}
