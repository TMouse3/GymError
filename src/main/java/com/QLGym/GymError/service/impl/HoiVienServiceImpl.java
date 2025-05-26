package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.dto.request.RegisterRequest;
import com.QLGym.GymError.dto.response.RegisterResponse;
import com.QLGym.GymError.entity.HoiVien;
import com.QLGym.GymError.repository.HoiVienRepository;
import com.QLGym.GymError.service.HoiVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HoiVienServiceImpl implements HoiVienService {
    @Autowired
    private HoiVienRepository hoiVienRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse registerHoiVien(RegisterRequest request) {
        // Kiểm tra trùng tài khoản
        if (hoiVienRepository.findByTaiKhoan(request.getTaiKhoan()).isPresent()) {
            return new RegisterResponse(false, "Tên đăng nhập đã tồn tại!");
        }
        // Kiểm tra trùng email
        if (hoiVienRepository.findByEmail(request.getEmail()).isPresent()) {
            return new RegisterResponse(false, "Email đã được sử dụng!");
        }
        // Kiểm tra trùng CCCD
        if (request.getCccd() != null && !request.getCccd().isEmpty() && hoiVienRepository.findByCccd(request.getCccd()).isPresent()) {
            return new RegisterResponse(false, "CCCD đã được sử dụng!");
        }
        // Kiểm tra xác nhận mật khẩu
        if (!request.getMatKhau().equals(request.getConfirmPassword())) {
            return new RegisterResponse(false, "Mật khẩu xác nhận không khớp!");
        }
        // Parse ngày sinh
        Date ngaySinh = null;
        try {
            if (request.getNgaySinh() != null && !request.getNgaySinh().isEmpty()) {
                ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(request.getNgaySinh());
            }
        } catch (ParseException e) {
            return new RegisterResponse(false, "Ngày sinh không hợp lệ!");
        }
        // Tạo hội viên mới
        HoiVien hoiVien = new HoiVien();
        hoiVien.setHoTen(request.getHoTen());
        hoiVien.setGioiTinh(request.getGioiTinh());
        hoiVien.setNgaySinh(ngaySinh);
        hoiVien.setSdt(request.getSdt());
        hoiVien.setEmail(request.getEmail());
        hoiVien.setCccd(request.getCccd());
        hoiVien.setTaiKhoan(request.getTaiKhoan());
        hoiVien.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        hoiVienRepository.save(hoiVien);
        return new RegisterResponse(true, "Đăng ký thành công!");
    }

    @Override
    public List<HoiVien> getAllHoiVien() {
        return hoiVienRepository.findAll();
    }
} 