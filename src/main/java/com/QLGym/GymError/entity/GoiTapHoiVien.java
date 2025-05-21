package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "GoiTapHoiVien")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoiTapHoiVien {
    @EmbeddedId
    private GoiTapHoiVienId id;

    @ManyToOne
    @MapsId("maHoiVien")
    @JoinColumn(name = "MaHoiVien")
    private HoiVien hoiVien;

    @ManyToOne
    @MapsId("maGoiTap")
    @JoinColumn(name = "MaGoiTap")
    private GoiTap goiTap;

    @Column(name = "NgayBatDau")
    @Temporal(TemporalType.DATE)
    private Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    @Temporal(TemporalType.DATE)
    private Date ngayKetThuc;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class GoiTapHoiVienId implements java.io.Serializable {
    private Integer maHoiVien;
    private Integer maGoiTap;
} 