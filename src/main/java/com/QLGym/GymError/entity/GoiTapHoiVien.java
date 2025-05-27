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
@Table(name = "GoiTapHoiVien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GoiTapHoiVien {
    @EqualsAndHashCode.Include
    @EmbeddedId
    private GoiTapHoiVienId id;

    @ManyToOne
    @MapsId("maHoiVien")
    @JoinColumn(name = "MaHoiVien")
    @JsonBackReference
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