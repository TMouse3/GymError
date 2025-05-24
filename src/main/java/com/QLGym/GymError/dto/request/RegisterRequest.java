package com.QLGym.GymError.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String hoTen;
    private Boolean gioiTinh;
    private String ngaySinh; // Sẽ convert sang Date ở backend
    private String sdt;
    private String email;
    private String cccd;
    private String taiKhoan;
    private String matKhau;
    private String confirmPassword;
} 