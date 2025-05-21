package com.QLGym.GymError.repository;

import com.QLGym.GymError.entity.HoiVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HoiVienRepository extends JpaRepository<HoiVien, Integer> {
    Optional<HoiVien> findByTaiKhoanAndMatKhau(String taiKhoan, String matKhau);
    Optional<HoiVien> findByTaiKhoan(String taiKhoan);
} 