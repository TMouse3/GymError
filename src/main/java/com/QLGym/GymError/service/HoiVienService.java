package com.QLGym.GymError.service;

import com.QLGym.GymError.dto.request.RegisterRequest;
import com.QLGym.GymError.dto.response.RegisterResponse;

import com.QLGym.GymError.entity.HoiVien;
import java.util.List;

public interface HoiVienService {
    RegisterResponse registerHoiVien(RegisterRequest request);
    List<HoiVien> getAllHoiVien();
} 