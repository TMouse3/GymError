package com.QLGym.GymError.controller;

import com.QLGym.GymError.entity.HoaDon;
import com.QLGym.GymError.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hoa-don")
public class HoaDonController {
    @Autowired
    private HoaDonRepository repository;

    @GetMapping
    public List<HoaDon> getAll() {
        return repository.findAll();
    }
} 