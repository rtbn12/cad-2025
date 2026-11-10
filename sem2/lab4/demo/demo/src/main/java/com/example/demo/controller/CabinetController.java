package com.example.demo.controller;

import com.example.demo.dto.CabinetView;
import com.example.demo.service.CabinetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'REGISTRAR')")
@Controller
public class CabinetController {

    private CabinetService cabinetService;

    public CabinetController(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @GetMapping("/")
    public String goCabinet(Authentication authentication, Model model){

        CabinetView cabinet = cabinetService.generateCabinetDto(authentication.getName());
        model.addAttribute("cabinet", cabinet);
        return "cabinet";
    }

//    @GetMapping("/logout")
//    public  String logout(){
//
//        return "redirect:/logout";
//    }
}
