package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.entity.NhanVienLeTan;
import com.QLGym.GymError.repository.NhanVienLeTanRepository;
import com.QLGym.GymError.service.NhanVienLeTanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienLeTanServiceImpl implements NhanVienLeTanService {

    @Autowired
    private NhanVienLeTanRepository nhanVienLeTanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<NhanVienLeTan> getAllNhanVienLeTan() {
        return nhanVienLeTanRepository.findAll();
    }

    @Override
    public NhanVienLeTan createNhanVienLeTan(NhanVienLeTan nhanVien) {
        // Encode password before saving
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
            
            // Only encode and update password if it's provided/changed
            if (nhanVienDetails.getMatKhau() != null && !nhanVienDetails.getMatKhau().isEmpty()) {
                 nhanVien.setMatKhau(passwordEncoder.encode(nhanVienDetails.getMatKhau()));
            }
            
            nhanVien.setTrangThaiLamViec(nhanVienDetails.getTrangThaiLamViec());
            return nhanVienLeTanRepository.save(nhanVien);
        } else {
            // Handle not found case, perhaps throw an exception
            return null; // Or throw ResourceNotFoundException
        }
    }

    @Override
    public void deleteNhanVienLeTan(Integer id) {
        nhanVienLeTanRepository.deleteById(id);
    }
} 