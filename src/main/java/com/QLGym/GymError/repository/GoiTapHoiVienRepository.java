package com.QLGym.GymError.repository;

import com.QLGym.GymError.entity.GoiTapHoiVien;
import com.QLGym.GymError.entity.GoiTapHoiVienId;
import com.QLGym.GymError.entity.HoiVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GoiTapHoiVienRepository extends JpaRepository<GoiTapHoiVien, GoiTapHoiVienId> {
    List<GoiTapHoiVien> findByHoiVienAndNgayKetThucAfter(HoiVien hoiVien, Date ngayKetThuc);
}