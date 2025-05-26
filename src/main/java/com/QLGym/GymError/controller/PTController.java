package com.QLGym.GymError.controller;

import com.QLGym.GymError.entity.PT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.QLGym.GymError.service.PTService;

@RestController
@RequestMapping("/api/pt")
public class PTController {
    @Autowired
    private PTService ptService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<PT> ptList = ptService.getAllPT();
            return ResponseEntity.ok(ptList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi lấy danh sách PT: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PT pt) {
        try {
            PT savedPt = ptService.createPT(pt);
            return ResponseEntity.ok(savedPt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi thêm PT: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PT pt) {
        try {
            PT updatedPt = ptService.updatePT(id, pt);
            if (updatedPt != null) {
                return ResponseEntity.ok(updatedPt);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Không tìm thấy PT với ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi cập nhật PT: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            ptService.deletePT(id);
            return ResponseEntity.ok(Map.of("message", "Xóa PT thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi xóa PT: " + e.getMessage()));
        }
    }
} 