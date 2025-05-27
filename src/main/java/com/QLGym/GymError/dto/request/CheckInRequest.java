package com.QLGym.GymError.dto.request;

import lombok.Data;

@Data
public class CheckInRequest {
    private Integer maHoiVien;
    private Integer maNhanVienLeTan;
    private String buoiCheckIn;
} 