package com.QLGym.GymError.dto.response;

import lombok.Data;

@Data
public class CheckInResponse {
    private boolean success;
    private String message;
    private String hoTenHoiVien; // Tên hội viên
    private String tenGoiTap; // Tên gói tập nếu có
    private String ngayKetThucGoiTap; // Ngày kết thúc gói tập nếu có
    private Integer soBuoiConLai; // Số buổi còn lại nếu là gói buổi (nếu có)
    private String checkInTime; // Thời gian check-in
} 