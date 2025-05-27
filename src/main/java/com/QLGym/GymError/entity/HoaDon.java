package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity
@Table(name = "HoaDon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
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