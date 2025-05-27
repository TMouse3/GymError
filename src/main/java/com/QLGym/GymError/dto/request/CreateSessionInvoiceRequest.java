package com.QLGym.GymError.dto.request;

import lombok.Data;

@Data
public class CreateSessionInvoiceRequest {
    private String tenHoaDon;
    private Double giaTien;
    private String ghiChu;
    private Integer maNhanVienLeTan;
} 