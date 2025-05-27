package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity
@Table(name = "HoaDon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HoaDon {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHoaDon")
    private Integer maHoaDon;

    @Column(name = "TenHoaDon", length = 50)
    private String tenHoaDon;

    @Column(name = "NgayThanhToan")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayThanhToan;

    @Column(name = "GiaTien")
    private Double giaTien;

    @Column(name = "GhiChu", length = 200)
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVienLeTan nhanVienLeTan;

    @ManyToOne
    @JoinColumn(name = "MaHoiVien")
    @JsonBackReference
    private HoiVien hoiVien;

    @ManyToOne
    @JoinColumn(name = "MaGoiTap")
    private GoiTap goiTap;
}