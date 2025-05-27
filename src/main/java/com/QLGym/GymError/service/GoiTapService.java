package com.QLGym.GymError.service;

import com.QLGym.GymError.dto.GoiTapDto;
import com.QLGym.GymError.entity.GoiTap;

import java.util.List;

public interface GoiTapService {
    List<GoiTapDto> getAllGoiTap();
    GoiTapDto getGoiTapById(Integer id);
    GoiTap createGoiTap(GoiTap goiTap);
    GoiTap updateGoiTap(Integer id, GoiTap goiTapDetails);
    void deleteGoiTap(Integer id);
} 