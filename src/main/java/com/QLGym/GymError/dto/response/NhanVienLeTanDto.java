package com.QLGym.GymError.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienLeTanDto {
    private Integer maNhanVien;
    private String hoTen;
    private Boolean gioiTinh;
    private Date ngaySinh;
    private String sdt;
    private String email;
    private String cccd;
    private String taiKhoan;
    private Boolean trangThaiLamViec;
} 