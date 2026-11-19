package com.example.demo.service;

import com.example.demo.dto.DateSelectView;
import com.example.demo.dto.DoctorSelectionView;
import com.example.demo.dto.SpecialityView;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SearchRegistrarService {

    private final SpecialityService specialityService;
    private final UserRepository userRepository;
    private final DoctorsService doctorsService;
    private final DateSlotService dateSlotService;


    public SearchRegistrarService(SpecialityService specialityService, UserRepository userRepository,
                                  DoctorsService doctorsService, DateSlotService dateSlotService) {

        this.specialityService = specialityService;
        this.userRepository = userRepository;
        this.doctorsService = doctorsService;
        this.dateSlotService = dateSlotService;
    }

    @Transactional(readOnly = true)
    public  List<SpecialityView> getSpecialityList(String currentUserLogin){
        baseCheck(currentUserLogin);
        return  specialityService.getSpecialitiesList();
    }

    @Transactional(readOnly = true)
    public DoctorSelectionView getDoctorsView(String currentUserLogin, Long specialityId){
        baseCheck(currentUserLogin);
        return doctorsService.getDoctorSelectViews(specialityId);
    }

    @Transactional(readOnly = true)
    public DateSelectView getDates(String currentUserLogin, Long selectDoctorId){
        baseCheck(currentUserLogin);
        return dateSlotService.getDataSelectView(selectDoctorId, 1);
    }





    private void baseCheck(String currentUserLogin){

        User currentUser = userRepository.findByLogin(currentUserLogin).orElseThrow(()->
                new UserNotFoundException("Пользователь не найден"));

        if (!currentUser.getRole().equals(UserRole.REGISTRAR)){

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Недостаточно прав");
        }
    }
}
