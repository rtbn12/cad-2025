package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.UserRole;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.CabinetService;
import com.example.demo.service.PatientRegistrationResult;
import com.example.demo.service.PatientCabinetUpdateService;
import jakarta.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'REGISTRAR')")
@Controller
public class CabinetController {

    private final CabinetService cabinetService;
    private final AppointmentService appointmentService;
    private final PatientCabinetUpdateService patientCabinetUpdateService;

    public CabinetController(CabinetService cabinetService,
                             AppointmentService appointmentService,
                             PatientCabinetUpdateService patientCabinetUpdateService) {

        this.cabinetService = cabinetService;
        this.appointmentService = appointmentService;
        this.patientCabinetUpdateService = patientCabinetUpdateService;
    }

    @GetMapping("/")
    public String goCabinet(Authentication authentication, Model model){

        CabinetView cabinet = cabinetService.generateCabinetDto(authentication.getName());

        List<AppointmentMiniView> miniAppointmentList = List.of();

        if (cabinet.getRole() == UserRole.PATIENT || cabinet.getRole() == UserRole.DOCTOR) {
            miniAppointmentList = appointmentService.getMyAppointments(authentication.getName());
        }

        model.addAttribute("cabinet", cabinet);
        model.addAttribute("miniAppointmentList", miniAppointmentList);
        return "cabinet";
    }

    @GetMapping("/appointmentDetails")
    public String goAppointmentDetails(@RequestParam("currentAppointmentId") Long currentAppointmentId,
                                       Authentication authentication, Model model){

        String currentUserLogin = authentication.getName();

        AppointmentDetailsView currentAppointmentDetailsView =
                appointmentService.getDetailView(currentAppointmentId,currentUserLogin);

        boolean doctorView = authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_DOCTOR"));

        model.addAttribute("doctorView", doctorView);

        model.addAttribute("currentAppointmentDetailsView", currentAppointmentDetailsView);

        return "appointmentDetails";
    }

    @PostMapping("/cancelAppointment")
    public String cancelAppointment(@RequestParam("currentAppointmentId") Long currentAppointmentId,
                                    Authentication authentication){

        String currentUserLogin = authentication.getName();

        appointmentService.cancelAppointment(currentAppointmentId,currentUserLogin);

        return "redirect:/cancellationSuccess";
    }

    @GetMapping("/cancellationSuccess")
    public String cancellationSuccess() {
        return "checkCanceled";
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/startUpdatePatientProfile")
    public String startUpdatePatientProfile(Authentication authentication, Model model){

        PatientProfileUpdateFormDto patientProfileForUpdate = patientCabinetUpdateService.
                getPatientProfileForUpdate(authentication.getName());

        model.addAttribute("updateForm", patientProfileForUpdate);

        return "updatePatientProfileForm";

    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/updateProfileResult")
    public String updateProfileTrueResult(@Valid @ModelAttribute("updateForm") PatientProfileUpdateFormDto updateForm,
                                          BindingResult bindingResult,
                                          Authentication authentication)
    {
        if (bindingResult.hasErrors()) {
            return "updatePatientProfileForm";
        }

        if (updateForm.isChangePassword() && !java.util.Objects.equals(updateForm.getPatientNewPassword(), updateForm.getPatientNewPasswordTwo())) {
            bindingResult.rejectValue(
                    "patientNewPasswordTwo",
                    "password.mismatch",
                    "Пароли не совпадают"
            );
            return "updatePatientProfileForm";
        }

        PatientRegistrationResult result;

        try {
            result = patientCabinetUpdateService.updateUserProfile(authentication.getName(), updateForm);
        } catch (DataIntegrityViolationException exception) {
            String constraintName = findConstraintName(exception);

            if ("patients_oms_number_key".equals(constraintName)) {
                result = PatientRegistrationResult.OMS_TAKEN;
            }else if ("patients_phone_key".equals(constraintName)) {
                result = PatientRegistrationResult.PHONE_TAKEN;}
            else {
                throw exception;
            }
        }

        if (result.equals(PatientRegistrationResult.SUCCESS)){

            return "redirect:/endUpdatePatientProfile";


        }else if (result.equals(PatientRegistrationResult.PHONE_TAKEN)) {

            bindingResult.rejectValue(
                    "patientPhone",
                    "phone.taken",
                    "Этот номер телефона уже используется"
            );
            return "updatePatientProfileForm";

        } else{

            bindingResult.rejectValue(
                    "patientOmsNumber",
                    "omsNumber.taken",
                    "Этот номер ОМС уже используется"
            );
            return "updatePatientProfileForm";
        }

    }

    @GetMapping("/endUpdatePatientProfile")
    public String goToEndUpdateProfile(){

        return "endUpdatePatientProfileForm";
    }

    private String findConstraintName(Throwable exception) {
        Throwable cause = exception;

        while (cause != null) {
            if (cause instanceof ConstraintViolationException violation) {
                return violation.getConstraintName();
            }

            cause = cause.getCause();
        }

        return null;
    }

//    @GetMapping("/logout")
//    public  String logout(){
//
//        return "redirect:/logout";
//    }
}
