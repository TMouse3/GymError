package com.QLGym.GymError.service;

import com.QLGym.GymError.dto.request.RegisterRequest;
import com.QLGym.GymError.dto.response.RegisterResponse;

public interface HoiVienService {
    RegisterResponse registerHoiVien(RegisterRequest request);
} 