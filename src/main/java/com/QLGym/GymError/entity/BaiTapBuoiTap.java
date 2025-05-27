package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BaiTapBuoiTap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaiTapBuoiTap {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaBaiTapBuoiTap")
    private Integer maBaiTapBuoiTap;

    @ManyToOne
    @JoinColumn(name = "MaBuoiTap")
    private BuoiTap buoiTap;

    @ManyToOne
    @JoinColumn(name = "MaBaiTap")
    private BaiTap baiTap;
}