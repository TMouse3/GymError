package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "CheckIn")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCheckIn")
    private Integer maCheckIn;

    @Column(name = "NgayCheckIn")
    @Temporal(TemporalType.DATE)
    private Date ngayCheckIn;

    @Column(name = "BuoiCheckIn", length = 10)
    private String buoiCheckIn;

    @ManyToOne
    @JoinColumn(name = "MaHoiVien")
    private HoiVien hoiVien;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVienLeTan nhanVienLeTan;
} 