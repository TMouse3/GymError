package com.QLGym.GymError.service;

import com.QLGym.GymError.entity.NhanVienLeTan;

import java.util.List;

public interface NhanVienLeTanService {
    List<NhanVienLeTan> getAllNhanVienLeTan();
    NhanVienLeTan createNhanVienLeTan(NhanVienLeTan nhanVien);
    NhanVienLeTan updateNhanVienLeTan(Integer id, NhanVienLeTan nhanVienDetails);
    void deleteNhanVienLeTan(Integer id);
} 