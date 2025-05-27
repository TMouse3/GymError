package com.QLGym.GymError.service;

import com.QLGym.GymError.dto.request.RegisterRequest;
import com.QLGym.GymError.dto.response.RegisterResponse;

import com.QLGym.GymError.entity.HoiVien;
import com.QLGym.GymError.dto.HoiVienDto;
import java.util.List;

public interface HoiVienService {
    RegisterResponse registerHoiVien(RegisterRequest request);
    List<HoiVienDto> getAllHoiVien();
    HoiVienDto getHoiVienById(Integer id);
    HoiVien addHoiVien(HoiVien hoiVien);
    HoiVien updateHoiVien(Integer id, HoiVien hoiVienDetails);
    void deleteHoiVien(Integer id);
} 