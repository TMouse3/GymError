package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "BaiTap")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaiTap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBaiTap")
    private Integer maBaiTap;

    @Column(name = "TenBaiTap", length = 50)
    private String tenBaiTap;

    @Column(name = "MoTaBaiTap", length = 200)
    private String moTaBaiTap;

    @OneToMany(mappedBy = "baiTap")
    private List<BaiTapBuoiTap> baiTapBuoiTaps;
} 