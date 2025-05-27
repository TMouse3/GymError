package com.QLGym.GymError.dto;

import lombok.Data;

@Data
public class HoiVienDto {
    private Integer maHoiVien;
    private String hoTen;
    private Boolean gioiTinh;
    private String ngaySinh;
    private String sdt;
    private String email;
    private String cccd;
    private String taiKhoan;
    private String activePackageName;
    private String activePackageEndDate;
    // No password field in DTO for listing
} 