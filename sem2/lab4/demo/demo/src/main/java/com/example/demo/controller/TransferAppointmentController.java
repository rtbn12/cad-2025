package com.example.demo.controller;

import com.example.demo.dto.TransferAppointmentView;
import com.example.demo.dto.TransferTimeView;
import com.example.demo.service.AppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@PreAuthorize("hasAnyRole('PATIENT', 'REGISTRAR')")
public class TransferAppointmentController {

    private final AppointmentService appointmentService;

    public TransferAppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/transferSelectDate")
    public String startTransfer(
            @RequestParam("transferAppointmentId") Long transferAppointmentId,
            Authentication authentication,
            Model model) {

        TransferAppointmentView dto = appointmentService.getTransferAppointment(transferAppointmentId,
                authentication.getName());

        model.addAttribute("transferAppointmentView", dto);

        return "transferSelectDate";
    }

    @GetMapping("/transferSelectTime")
    public String selectNewDate( @RequestParam("transferAppointmentId") Long transferAppointmentId,
                                 @RequestParam("selectDate") LocalDate selectDate,
                                 Authentication authentication, Model model){

        TransferTimeView currentTransferTimeView = appointmentService.getTransferTimeView(
                transferAppointmentId, selectDate, authentication.getName());

        model.addAttribute("transferTimeView", currentTransferTimeView);

        return  "transferSelectTime";
    }


}
