package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;

@Entity
@Table(name = "CheckIn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCheckIn")
    @EqualsAndHashCode.Include
    private Integer maCheckIn;

    @Column(name = "NgayCheckIn")
    @Temporal(TemporalType.DATE)
    private Date ngayCheckIn;

    @Column(name = "BuoiCheckIn", length = 10)
    private String buoiCheckIn;

    @ManyToOne
    @JoinColumn(name = "MaHoiVien")
    @JsonBackReference
    private HoiVien hoiVien;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVienLeTan nhanVienLeTan;
}