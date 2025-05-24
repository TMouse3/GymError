package com.QLGym.GymError.repository;

import com.QLGym.GymError.entity.GoiTap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoiTapRepository extends JpaRepository<GoiTap, Integer> {
} 