package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PT {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPT")
    private Integer maPT;

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

    @Column(name = "BangCap", length = 200)
    private String bangCap;

    @Column(name = "KinhNghiem")
    private Integer kinhNghiem;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @Column(name = "TaiKhoan", length = 20, unique = true)
    private String taiKhoan;

    @Column(name = "MatKhau", length = 200)
    private String matKhau;

    @Column(name = "TrangThaiLamViec")
    private Boolean trangThaiLamViec;

    @OneToMany(mappedBy = "pt")
    private List<BuoiTap> buoiTaps;
}