package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "BaiTap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaiTap {
    @EqualsAndHashCode.Include
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