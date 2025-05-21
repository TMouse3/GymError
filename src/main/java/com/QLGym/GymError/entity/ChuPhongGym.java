package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ChuPhongGym")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChuPhongGym {
    @Id
    @Column(name = "TaiKhoan", length = 20)
    private String taiKhoan;

    @Column(name = "MatKhau", length = 200, nullable = false)
    private String matKhau;
} 