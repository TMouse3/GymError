package com.QLGym.GymError.controller;

import com.QLGym.GymError.entity.PT;
import com.QLGym.GymError.repository.PTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pt")
public class PTController {
    @Autowired
    private PTRepository repository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<PT> ptList = repository.findAll();
            return ResponseEntity.ok(ptList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi lấy danh sách PT: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PT pt) {
        try {
            PT savedPt = repository.save(pt);
            return ResponseEntity.ok(savedPt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi thêm PT: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PT pt) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Không tìm thấy PT với ID: " + id));
            }
            pt.setMaPT(id);
            PT updatedPt = repository.save(pt);
            return ResponseEntity.ok(updatedPt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi cập nhật PT: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Không tìm thấy PT với ID: " + id));
            }
            repository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Xóa PT thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi xóa PT: " + e.getMessage()));
        }
    }
} 