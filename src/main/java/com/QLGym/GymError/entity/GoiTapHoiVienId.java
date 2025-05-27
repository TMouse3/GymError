package com.QLGym.GymError.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoiTapHoiVienId implements Serializable {
    private Integer maHoiVien;
    private Integer maGoiTap;
} 