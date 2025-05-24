package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "NhanVienLeTan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienLeTan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNhanVien")
    private Integer maNhanVien;

    @Column(name = "HoTen", length = 50)
    private String hoTen;

    @Column(name = "GioiTinh")
    private Boolean gioiTinh;

    @Column(name = "NgaySinh")
    @Temporal(TemporalType.DATE)
    private Date ngaySinh;

    @Column(name = "SDT", length = 10)
    private String sdt;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "CCCD", length = 12, unique = true)
    private String cccd;

    @Column(name = "TaiKhoan", length = 20, unique = true)
    private String taiKhoan;

    @Column(name = "MatKhau", length = 200)
    private String matKhau;

    @Column(name = "TrangThaiLamViec")
    private Boolean trangThaiLamViec;

    @OneToMany(mappedBy = "nhanVienLeTan")
    private List<CheckIn> checkIns;

    @OneToMany(mappedBy = "nhanVienLeTan")
    private List<HoaDon> hoaDons;

    public void setId(Integer id) {
        this.maNhanVien = id;
    }
} 