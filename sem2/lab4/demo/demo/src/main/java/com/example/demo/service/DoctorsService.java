package com.example.demo.service;

import com.example.demo.dto.DoctorSelectionView;
import com.example.demo.dto.DoctorView;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Speciality;
import com.example.demo.exception.SpecialityNotFoundException;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.SpecialityRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorsService {

    private final DoctorRepository doctorRepository;
    private final SpecialityRepository specialityRepository;

    public DoctorsService(DoctorRepository doctorRepository, SpecialityRepository specialityRepository) {
        this.doctorRepository = doctorRepository;
        this.specialityRepository = specialityRepository;
    }

    public DoctorSelectionView getDoctorSelectViews(Long specialityId){

        Speciality speciality = specialityRepository.findById(specialityId).orElseThrow(() ->
                new SpecialityNotFoundException("Специальность не найдена"));

        String specialityName = speciality.getName();

        List<DoctorView> dto = new ArrayList<>();

        List<Doctor> allDoctors = doctorRepository.findAllBySpeciality_Id(specialityId);

        for(Doctor doctor : allDoctors){

            dto.add(new DoctorView(doctor.getFirstName(), doctor.getMiddleName(), doctor.getLastName(),doctor.getId()));

        }

        DoctorSelectionView doctorSelectionView = new DoctorSelectionView(specialityName, specialityId, dto);

        return doctorSelectionView;
    }
}
