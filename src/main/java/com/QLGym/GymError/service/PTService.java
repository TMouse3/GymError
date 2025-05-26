package com.QLGym.GymError.service;

import com.QLGym.GymError.entity.PT;

import java.util.List;

public interface PTService {
    List<PT> getAllPT();
    PT createPT(PT pt);
    PT updatePT(Integer id, PT ptDetails);
    void deletePT(Integer id);
} 