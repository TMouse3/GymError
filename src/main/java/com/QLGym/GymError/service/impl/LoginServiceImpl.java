package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.dto.request.LoginRequest;
import com.QLGym.GymError.dto.response.LoginResponse;
import com.QLGym.GymError.entity.ChuPhongGym;
import com.QLGym.GymError.entity.PT;
import com.QLGym.GymError.entity.NhanVienLeTan;
import com.QLGym.GymError.entity.HoiVien;
import com.QLGym.GymError.repository.ChuPhongGymRepository;
import com.QLGym.GymError.repository.PTRepository;
import com.QLGym.GymError.repository.NhanVienLeTanRepository;
import com.QLGym.GymError.repository.HoiVienRepository;
import com.QLGym.GymError.config.JwtUtil;
import com.QLGym.GymError.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ChuPhongGymRepository chuPhongGymRepository;
    
    @Autowired
    private PTRepository ptRepository;
    
    @Autowired
    private NhanVienLeTanRepository nhanVienLeTanRepository;
    
    @Autowired
    private HoiVienRepository hoiVienRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String role = loginRequest.getRole();

        boolean isAuthenticated = false;
        String authenticatedRole = null;

        switch (role.toUpperCase()) {
            case "CHUPHONG":
                Optional<ChuPhongGym> chuPhongGym = chuPhongGymRepository.findByTaiKhoan(username);
                if (chuPhongGym.isPresent() && passwordEncoder.matches(password, chuPhongGym.get().getMatKhau())) {
                    isAuthenticated = true;
                    authenticatedRole = "CHUPHONG";
                }
                break;
            case "PT":
                Optional<PT> pt = ptRepository.findByTaiKhoan(username);
                if (pt.isPresent() && passwordEncoder.matches(password, pt.get().getMatKhau())) {
                    isAuthenticated = true;
                    authenticatedRole = "PT";
                }
                break;
            case "NHANVIEN":
                Optional<NhanVienLeTan> nhanVienLeTan = nhanVienLeTanRepository.findByTaiKhoan(username);
                if (nhanVienLeTan.isPresent() && passwordEncoder.matches(password, nhanVienLeTan.get().getMatKhau())) {
                    isAuthenticated = true;
                    authenticatedRole = "NHANVIEN";
                }
                break;
            case "HOIVIEN":
                Optional<HoiVien> hoiVien = hoiVienRepository.findByTaiKhoan(username);
                if (hoiVien.isPresent() && passwordEncoder.matches(password, hoiVien.get().getMatKhau())) {
                    isAuthenticated = true;
                    authenticatedRole = "HOIVIEN";
                }
                break;
            default:
                throw new IllegalArgumentException("Vai trò đăng nhập không hợp lệ.");
        }

        if (isAuthenticated) {
            String token = jwtUtil.generateToken(username, authenticatedRole);
            return new LoginResponse(token, authenticatedRole, username);
        } else {
            throw new IllegalArgumentException("Sai tài khoản hoặc mật khẩu!");
        }
    }
} 