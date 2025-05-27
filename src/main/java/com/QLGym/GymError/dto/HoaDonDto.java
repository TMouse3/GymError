package com.QLGym.GymError.dto;

import lombok.Data;

import java.util.Date;

@Data
public class HoaDonDto {
    private Integer maHoaDon;
    private String tenHoaDon;
    private Date ngayThanhToan;
    private Double giaTien;
    private String ghiChu;
    private String tenHoiVien;
    private String tenGoiTap;
    private String tenNhanVien;
} 