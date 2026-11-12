package com.example.demo.controller;

import com.example.demo.dto.PatientRegistrationFormDto;
import com.example.demo.service.PatientRegistrationResult;
import com.example.demo.service.PatientRegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.dao.DataIntegrityViolationException;
import org.hibernate.exception.ConstraintViolationException;


@Controller
public class RegistrationController {


    private final PatientRegistrationService patientRegistrationService;

    public RegistrationController(PatientRegistrationService patientRegistrationService) {
        this.patientRegistrationService = patientRegistrationService;
    }

    @GetMapping("/registration")
    public String goRegistrarForm(Model model){

        model.addAttribute("registrationForm", new PatientRegistrationFormDto());

        return "registrarForm";
    }

    @PostMapping("/registrationResult")
    public String registrationTrueResult(@Valid @ModelAttribute("registrationForm")
                                             PatientRegistrationFormDto form, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return "registrarForm";
        }

        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            bindingResult.rejectValue(
                    "passwordConfirmation",
                    "password.mismatch",
                    "Пароли не совпадают"
            );
            return "registrarForm";
        }

        PatientRegistrationResult result;

        try {
            result = patientRegistrationService.savePatient(form);
        } catch (DataIntegrityViolationException exception) {
            String constraintName = findConstraintName(exception);

            if ("users_login_key".equals(constraintName)) {
                result = PatientRegistrationResult.LOGIN_TAKEN;
            } else if ("patients_oms_number_key".equals(constraintName)) {
                result = PatientRegistrationResult.OMS_TAKEN;
            } else {
                throw exception;
            }
        }

        if (result.equals(PatientRegistrationResult.SUCCESS)){

            return "redirect:/login";

        } else if (result.equals(PatientRegistrationResult.LOGIN_TAKEN)) {

            bindingResult.rejectValue(
                    "login",
                    "login.taken",
                    "Этот логин уже используется"
            );
            return "registrarForm";

        }else{

            bindingResult.rejectValue(
                    "omsNumber",
                    "omsNumber.taken",
                    "Этот номер ОМС уже используется"
            );
            return "registrarForm";
        }


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
}
