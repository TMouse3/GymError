package com.QLGym.GymError.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BaiTapBuoiTap")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaiTapBuoiTap {
    @EmbeddedId
    private BaiTapBuoiTapId id;

    @ManyToOne
    @MapsId("maBuoiTap")
    @JoinColumn(name = "MaBuoiTap")
    private BuoiTap buoiTap;

    @ManyToOne
    @MapsId("maBaiTap")
    @JoinColumn(name = "MaBaiTap")
    private BaiTap baiTap;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class BaiTapBuoiTapId implements java.io.Serializable {
    private Integer maBuoiTap;
    private Integer maBaiTap;
} 