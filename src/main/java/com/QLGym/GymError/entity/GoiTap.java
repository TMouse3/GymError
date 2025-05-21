package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "GoiTap")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoiTap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGoiTap")
    private Integer maGoiTap;

    @Column(name = "TenGoiTap", length = 50)
    private String tenGoiTap;

    @Column(name = "SoNgay")
    private Integer soNgay;

    @Column(name = "GiaGoiTap")
    private Double giaGoiTap;

    @Column(name = "TrangThaiGoiTap")
    private Boolean trangThaiGoiTap;

    @Column(name = "MoTaGoiTap", length = 200)
    private String moTaGoiTap;

    @OneToMany(mappedBy = "goiTap")
    private List<GoiTapHoiVien> goiTapHoiViens;

    @OneToMany(mappedBy = "goiTap")
    private List<HoaDon> hoaDons;
} 