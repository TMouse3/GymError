package com.QLGym.GymError.service;

import com.QLGym.GymError.entity.NhanVienLeTan;
import com.QLGym.GymError.dto.response.NhanVienLeTanDto;

import java.util.List;

public interface NhanVienLeTanService {
    List<NhanVienLeTanDto> getAllNhanVienLeTan();
    NhanVienLeTan createNhanVienLeTan(NhanVienLeTan nhanVien);
    NhanVienLeTan updateNhanVienLeTan(Integer id, NhanVienLeTan nhanVienDetails);
    void deleteNhanVienLeTan(Integer id);
} 