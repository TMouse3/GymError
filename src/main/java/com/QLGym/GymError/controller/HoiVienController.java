package com.QLGym.GymError.controller;

import com.QLGym.GymError.dto.request.RegisterRequest;
import com.QLGym.GymError.dto.response.RegisterResponse;
import com.QLGym.GymError.service.HoiVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HoiVienController {
    @Autowired
    private HoiVienService hoiVienService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return hoiVienService.registerHoiVien(request);
    }
} 