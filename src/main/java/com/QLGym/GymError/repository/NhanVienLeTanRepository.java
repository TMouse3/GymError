package com.QLGym.GymError.repository;

import com.QLGym.GymError.entity.NhanVienLeTan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienLeTanRepository extends JpaRepository<NhanVienLeTan, Integer> {
    Optional<NhanVienLeTan> findByTaiKhoanAndMatKhau(String taiKhoan, String matKhau);
    Optional<NhanVienLeTan> findByTaiKhoan(String taiKhoan);
} 