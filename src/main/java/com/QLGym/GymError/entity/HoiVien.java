package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "HoiVien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HoiVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHoiVien")
    @EqualsAndHashCode.Include
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
    @JsonManagedReference
    private List<BuoiTap> buoiTaps;

    @OneToMany(mappedBy = "hoiVien")
    @JsonManagedReference
    private List<CheckIn> checkIns;

    @OneToMany(mappedBy = "hoiVien")
    @JsonManagedReference
    private List<GoiTapHoiVien> goiTapHoiViens;

    @OneToMany(mappedBy = "hoiVien")
    @JsonManagedReference
    private List<HoaDon> hoaDons;
}