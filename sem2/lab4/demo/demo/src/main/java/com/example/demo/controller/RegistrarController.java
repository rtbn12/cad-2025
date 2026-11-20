package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.SearchRegistrarService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.example.demo.exception.PatientNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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

    @GetMapping("/regSelectTime")
    public String selectTimeView(@RequestParam("selectedDoctorId") Long selectedDoctorId,
                                 @RequestParam("selectedDate") LocalDate selectedDate,
                                 Authentication authentication, Model model){

        TimeSelectView timesView = searchRegistrarService.getTimes(authentication.getName(),
                selectedDoctorId, selectedDate);

        model.addAttribute("timesView", timesView);

        return "regSelectTimeView";
    }

    @GetMapping("/regGetAppointment")
    public String getAppointment(@RequestParam("currentSlotId") Long currentSlotId,
                                 Authentication authentication, Model model){

        AppointmentDetailsView appointmentDetailsView = searchRegistrarService.getAppointmentView(
                authentication.getName(), currentSlotId);

        model.addAttribute("appointmentDetailsView", appointmentDetailsView);

        return "regGetAppointmentView";
    }

    @GetMapping("/regStartSearchPatient")
    public String getStartSearchPatient(Authentication authentication, Model model){
        searchRegistrarService.baseCheck(authentication.getName());
        PatientSearchFormDto form = new PatientSearchFormDto();
        form.setSearchType(PatientSearchType.OMS);
        model.addAttribute("searchForm", form);
        return "regStartSearchPatientView";
    }

    @PostMapping("/regGetPatient")
    public String getPatientView(@Valid @ModelAttribute("searchForm") PatientSearchFormDto form,
                                 BindingResult bindingResult,
                                 Authentication authentication){
        if (bindingResult.hasErrors()) {
            return "regStartSearchPatientView";
        }
        Long patientUserId;
        try {
            patientUserId = searchRegistrarService.getSearchPatientId(authentication.getName(),
                    form.getSearchType(), form.getSearchValue());
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue("searchValue", "search.invalid", exception.getMessage());
            return "regStartSearchPatientView";
        } catch (PatientNotFoundException exception) {
            return "redirect:/regPatientNotFound";
        }
        return "redirect:/regGetPatientResult?patientUserId=" + patientUserId;
    }

    @GetMapping("/regPatientNotFound")
    public String patientNotFound(){
        return "regPatientNotFoundView";
    }

    @GetMapping("/regGetPatientResult")
    public String getStartSearchPatientResult(@RequestParam("patientUserId") Long patientUserId,
                                              Authentication authentication, Model model){
        SearchPatientMiniCabinetView patientCabinetView = searchRegistrarService.getPatientCabinetView(
                authentication.getName(), patientUserId);
        model.addAttribute("patientCabinetView", patientCabinetView);
        return "regGetPatientResultView";
    }
}
