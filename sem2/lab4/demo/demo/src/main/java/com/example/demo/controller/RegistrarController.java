package com.example.demo.controller;

import com.example.demo.dto.DateSelectView;
import com.example.demo.dto.DoctorSelectionView;
import com.example.demo.dto.SpecialityView;
import com.example.demo.service.SearchRegistrarService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@PreAuthorize("hasRole('REGISTRAR')")
public class RegistrarController {

    private final SearchRegistrarService searchRegistrarService;

    public RegistrarController(SearchRegistrarService searchRegistrarService) {
        this.searchRegistrarService = searchRegistrarService;
    }

    @GetMapping("/regSelectSpeciality")
    public String startDoctorSearch(Authentication authentication, Model model){

        List<SpecialityView> specialityList = searchRegistrarService.getSpecialityList(authentication.getName());

        model.addAttribute("specialityList", specialityList);

        return "regSelectSpecialityView";
    }

    @GetMapping("/regSelectDoctor")
    public String selectDoctorView(@RequestParam("selectedSpecialityId") Long selectedSpecialityId,
                                   Authentication authentication, Model model){

        DoctorSelectionView doctorsView = searchRegistrarService.getDoctorsView(authentication.getName(),
                selectedSpecialityId);

        model.addAttribute("doctorsView", doctorsView);

        return "regSelectDoctorView";
    }

    @GetMapping("/regSelectDate")
    public String selectDateView(@RequestParam("selectedDoctorId") Long selectedDoctorId,
                                   Authentication authentication, Model model){

        DateSelectView datesView = searchRegistrarService.getDates(authentication.getName(), selectedDoctorId);

        model.addAttribute("datesView", datesView);

        return "regSelectDateView";
    }


}
