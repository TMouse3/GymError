package com.QLGym.GymError.dto.request;

import lombok.Data;

@Data
public class CreatePackageInvoiceRequest {
    private Integer maHoiVien;
    private Integer maGoiTap;
    private String ghiChu;
    private Integer maNhanVienLeTan;
} 