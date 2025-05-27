package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BuoiTap")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuoiTap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBuoiTap")
    private Integer maBuoiTap;

    @ManyToOne
    @JoinColumn(name = "MaPT")
    private PT pt;

    @ManyToOne
    @JoinColumn(name = "MaHoiVien")
    @JsonBackReference
    private HoiVien hoiVien;

    @Column(name = "NgayTap")
    @Temporal(TemporalType.DATE)
    private Date ngayTap;

    @Column(name = "Buoi", length = 10)
    private String buoi;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "buoiTap")
    private List<BaiTapBuoiTap> baiTapBuoiTaps;
} 