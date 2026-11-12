package com.example.demo.service;

import com.example.demo.dto.SpecialityView;
import com.example.demo.entity.Speciality;
import com.example.demo.repository.SpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<SpecialityView> getSpecialitiesList(){

        List<SpecialityView> dto = new ArrayList<>();

        List<Speciality> allSpeciality = specialityRepository.findAll();

        for(Speciality speciality : allSpeciality){

            dto.add(new SpecialityView(speciality.getId(), speciality.getName()));

        }

        return dto;

    }
}
