package com.QLGym.GymError.repository;

import com.QLGym.GymError.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
} 