package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.entity.PT;
import com.QLGym.GymError.repository.PTRepository;
import com.QLGym.GymError.service.PTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PTServiceImpl implements PTService {

    @Autowired
    private PTRepository ptRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<PT> getAllPT() {
        return ptRepository.findAll();
    }

    @Override
    public PT createPT(PT pt) {
        // Encode password before saving
        pt.setMatKhau(passwordEncoder.encode(pt.getMatKhau()));
        return ptRepository.save(pt);
    }

    @Override
    public PT updatePT(Integer id, PT ptDetails) {
        Optional<PT> optionalPT = ptRepository.findById(id);
        if (optionalPT.isPresent()) {
            PT pt = optionalPT.get();
            pt.setHoTen(ptDetails.getHoTen());
            pt.setGioiTinh(ptDetails.getGioiTinh());
            pt.setNgaySinh(ptDetails.getNgaySinh());
            pt.setSdt(ptDetails.getSdt());
            pt.setEmail(ptDetails.getEmail());
            pt.setCccd(ptDetails.getCccd());
            pt.setBangCap(ptDetails.getBangCap());
            pt.setKinhNghiem(ptDetails.getKinhNghiem());
            pt.setTrangThai(ptDetails.getTrangThai());
            pt.setTaiKhoan(ptDetails.getTaiKhoan());

            // Only encode and update password if it's provided/changed
            if (ptDetails.getMatKhau() != null && !ptDetails.getMatKhau().isEmpty()) {
                 pt.setMatKhau(passwordEncoder.encode(ptDetails.getMatKhau()));
            }

            pt.setTrangThaiLamViec(ptDetails.getTrangThaiLamViec());
            return ptRepository.save(pt);
        } else {
            // Handle not found case, perhaps throw an exception
            return null; // Or throw ResourceNotFoundException
        }
    }

    @Override
    public void deletePT(Integer id) {
        ptRepository.deleteById(id);
    }
} 