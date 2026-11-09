package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT', 'REGISTRAR')")
@Controller
public class CabinetController {

    @GetMapping("/")
    public String goCabinet(){
        return "cabinet";
    }
}
