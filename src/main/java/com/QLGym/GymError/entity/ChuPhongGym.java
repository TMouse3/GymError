package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChuPhongGym")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChuPhongGym {
    @EqualsAndHashCode.Include
    @Id
    @Column(name = "TaiKhoan", length = 20)
    private String taiKhoan;

    @Column(name = "MatKhau", length = 200, nullable = false)
    private String matKhau;
}