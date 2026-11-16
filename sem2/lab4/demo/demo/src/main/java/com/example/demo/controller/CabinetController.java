package com.example.demo.controller;

import com.example.demo.dto.AppointmentDetailsView;
import com.example.demo.dto.AppointmentMiniView;
import com.example.demo.dto.CabinetView;
import com.example.demo.entity.UserRole;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.CabinetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'REGISTRAR')")
@Controller
public class CabinetController {

    private final CabinetService cabinetService;
    private final AppointmentService appointmentService;

    public CabinetController(CabinetService cabinetService,
                             AppointmentService appointmentService) {

        this.cabinetService = cabinetService;
        this.appointmentService = appointmentService;
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

//    @GetMapping("/logout")
//    public  String logout(){
//
//        return "redirect:/logout";
//    }
}
