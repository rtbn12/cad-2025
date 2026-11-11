package com.example.demo.controller;

import com.example.demo.dto.PatientRegistrationFormDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {

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

        return "redirect:/login";
    }
}
