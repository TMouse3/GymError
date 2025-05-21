package com.QLGym.GymError.repository;

import com.QLGym.GymError.entity.ChuPhongGym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChuPhongGymRepository extends JpaRepository<ChuPhongGym, String> {
    Optional<ChuPhongGym> findByTaiKhoanAndMatKhau(String taiKhoan, String matKhau);
    Optional<ChuPhongGym> findByTaiKhoan(String taiKhoan);
}