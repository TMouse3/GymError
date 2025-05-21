package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "HoiVien")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoiVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHoiVien")
    private Integer maHoiVien;

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

    @OneToMany(mappedBy = "hoiVien")
    private List<BuoiTap> buoiTaps;

    @OneToMany(mappedBy = "hoiVien")
    private List<CheckIn> checkIns;

    @OneToMany(mappedBy = "hoiVien")
    private List<GoiTapHoiVien> goiTapHoiViens;

    @OneToMany(mappedBy = "hoiVien")
    private List<HoaDon> hoaDons;
} 