package com.QLGym.GymError.repository;

import com.QLGym.GymError.entity.PT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PTRepository extends JpaRepository<PT, Integer> {
    Optional<PT> findByTaiKhoanAndMatKhau(String taiKhoan, String matKhau);
    Optional<PT> findByTaiKhoan(String taiKhoan);
} 