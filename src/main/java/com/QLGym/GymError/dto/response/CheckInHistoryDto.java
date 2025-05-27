package com.QLGym.GymError.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class CheckInHistoryDto {
    private Integer maCheckIn;
    private Date checkInTime; // Assuming this is the timestamp of check-in
    private String buoiCheckIn;
    private String hoTenHoiVien;
    private String cccdHoiVien;
    private String hoTenNhanVien;
} 