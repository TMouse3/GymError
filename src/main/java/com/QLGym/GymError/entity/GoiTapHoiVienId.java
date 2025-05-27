package com.QLGym.GymError.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GoiTapHoiVienId implements Serializable {
    @EqualsAndHashCode.Include
    private Integer maHoiVien;
    @EqualsAndHashCode.Include
    private Integer maGoiTap;
}