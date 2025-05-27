package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.entity.NhanVienLeTan;
import com.QLGym.GymError.dto.response.NhanVienLeTanDto;
import com.QLGym.GymError.repository.NhanVienLeTanRepository;
import com.QLGym.GymError.service.NhanVienLeTanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NhanVienLeTanServiceImpl implements NhanVienLeTanService {

    @Autowired
    private NhanVienLeTanRepository nhanVienLeTanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<NhanVienLeTanDto> getAllNhanVienLeTan() {
        return nhanVienLeTanRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NhanVienLeTanDto convertToDto(NhanVienLeTan nhanVien) {
        NhanVienLeTanDto dto = new NhanVienLeTanDto();
        dto.setMaNhanVien(nhanVien.getMaNhanVien());
        dto.setHoTen(nhanVien.getHoTen());
        dto.setGioiTinh(nhanVien.getGioiTinh());
        dto.setNgaySinh(nhanVien.getNgaySinh());
        dto.setSdt(nhanVien.getSdt());
        dto.setEmail(nhanVien.getEmail());
        dto.setCccd(nhanVien.getCccd());
        dto.setTaiKhoan(nhanVien.getTaiKhoan());
        dto.setTrangThaiLamViec(nhanVien.getTrangThaiLamViec());
        return dto;
    }

    @Override
    public NhanVienLeTan createNhanVienLeTan(NhanVienLeTan nhanVien) {
        nhanVien.setMatKhau(passwordEncoder.encode(nhanVien.getMatKhau()));
        return nhanVienLeTanRepository.save(nhanVien);
    }

    @Override
    public NhanVienLeTan updateNhanVienLeTan(Integer id, NhanVienLeTan nhanVienDetails) {
        Optional<NhanVienLeTan> optionalNhanVien = nhanVienLeTanRepository.findById(id);
        if (optionalNhanVien.isPresent()) {
            NhanVienLeTan nhanVien = optionalNhanVien.get();
            nhanVien.setHoTen(nhanVienDetails.getHoTen());
            nhanVien.setGioiTinh(nhanVienDetails.getGioiTinh());
            nhanVien.setNgaySinh(nhanVienDetails.getNgaySinh());
            nhanVien.setSdt(nhanVienDetails.getSdt());
            nhanVien.setEmail(nhanVienDetails.getEmail());
            nhanVien.setCccd(nhanVienDetails.getCccd());
            nhanVien.setTaiKhoan(nhanVienDetails.getTaiKhoan());
            
            if (nhanVienDetails.getMatKhau() != null && !nhanVienDetails.getMatKhau().isEmpty()) {
                 nhanVien.setMatKhau(passwordEncoder.encode(nhanVienDetails.getMatKhau()));
            }
            
            nhanVien.setTrangThaiLamViec(nhanVienDetails.getTrangThaiLamViec());
            return nhanVienLeTanRepository.save(nhanVien);
        } else {
            return null;
        }
    }

    @Override
    public void deleteNhanVienLeTan(Integer id) {
        nhanVienLeTanRepository.deleteById(id);
    }
} 