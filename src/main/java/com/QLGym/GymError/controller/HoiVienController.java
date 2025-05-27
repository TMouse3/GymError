package com.QLGym.GymError.controller;

import com.QLGym.GymError.dto.request.RegisterRequest;
import com.QLGym.GymError.dto.response.RegisterResponse;
import com.QLGym.GymError.service.HoiVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.QLGym.GymError.entity.HoiVien;
import com.QLGym.GymError.dto.HoiVienDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class HoiVienController {
    @Autowired
    private HoiVienService hoiVienService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return hoiVienService.registerHoiVien(request);
    }

    @GetMapping("/hoi-vien")
    public List<HoiVienDto> getAllHoiVien() {
        return hoiVienService.getAllHoiVien();
    }

    @GetMapping("/hoi-vien/{id}")
    public ResponseEntity<HoiVienDto> getHoiVienById(@PathVariable Integer id) {
        HoiVienDto hoiVienDto = hoiVienService.getHoiVienById(id);
        if (hoiVienDto != null) {
            return ResponseEntity.ok(hoiVienDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/hoi-vien")
    public ResponseEntity<HoiVien> addHoiVien(@RequestBody HoiVien hoiVien) {
        HoiVien newHoiVien = hoiVienService.addHoiVien(hoiVien);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHoiVien);
    }

    @PutMapping("/hoi-vien/{id}")
    public ResponseEntity<HoiVien> updateHoiVien(@PathVariable Integer id, @RequestBody HoiVien hoiVienDetails) {
        try {
            HoiVien updatedHoiVien = hoiVienService.updateHoiVien(id, hoiVienDetails);
            return ResponseEntity.ok(updatedHoiVien);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/hoi-vien/{id}")
    public ResponseEntity<Void> deleteHoiVien(@PathVariable Integer id) {
        try {
            hoiVienService.deleteHoiVien(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
} 