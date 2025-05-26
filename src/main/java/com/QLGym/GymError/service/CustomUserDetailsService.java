package com.QLGym.GymError.service;

import com.QLGym.GymError.entity.ChuPhongGym;
import com.QLGym.GymError.entity.HoiVien;
import com.QLGym.GymError.entity.NhanVienLeTan;
import com.QLGym.GymError.entity.PT;
import com.QLGym.GymError.repository.ChuPhongGymRepository;
import com.QLGym.GymError.repository.HoiVienRepository;
import com.QLGym.GymError.repository.NhanVienLeTanRepository;
import com.QLGym.GymError.repository.PTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ChuPhongGymRepository chuPhongGymRepository;

    @Autowired
    private HoiVienRepository hoiVienRepository;

    @Autowired
    private NhanVienLeTanRepository nhanVienLeTanRepository;

    @Autowired
    private PTRepository ptRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ChuPhongGym> admin = chuPhongGymRepository.findByTaiKhoan(username);
        if (admin.isPresent()) {
            return new User(admin.get().getTaiKhoan(), admin.get().getMatKhau(), new ArrayList<>());
        }

        Optional<HoiVien> hoiVien = hoiVienRepository.findByTaiKhoan(username);
        if (hoiVien.isPresent()) {
            return new User(hoiVien.get().getTaiKhoan(), hoiVien.get().getMatKhau(), new ArrayList<>());
        }

        Optional<NhanVienLeTan> nhanVien = nhanVienLeTanRepository.findByTaiKhoan(username);
        if (nhanVien.isPresent()) {
            return new User(nhanVien.get().getTaiKhoan(), nhanVien.get().getMatKhau(), new ArrayList<>());
        }

        Optional<PT> pt = ptRepository.findByTaiKhoan(username);
        if (pt.isPresent()) {
            return new User(pt.get().getTaiKhoan(), pt.get().getMatKhau(), new ArrayList<>());
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
} 