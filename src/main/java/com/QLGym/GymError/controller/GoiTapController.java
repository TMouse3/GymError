package com.QLGym.GymError.controller;

import com.QLGym.GymError.entity.GoiTap;
import com.QLGym.GymError.repository.GoiTapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goi-tap")
public class GoiTapController {
    @Autowired
    private GoiTapRepository repository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<GoiTap> goiTapList = repository.findAll();
            return ResponseEntity.ok(goiTapList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi lấy danh sách gói tập: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GoiTap goiTap) {
        try {
            GoiTap savedGoiTap = repository.save(goiTap);
            return ResponseEntity.ok(savedGoiTap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi thêm gói tập: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GoiTap goiTap) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Không tìm thấy gói tập với ID: " + id));
            }
            goiTap.setMaGoiTap(id);
            GoiTap updatedGoiTap = repository.save(goiTap);
            return ResponseEntity.ok(updatedGoiTap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi cập nhật gói tập: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            if (!repository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Không tìm thấy gói tập với ID: " + id));
            }
            repository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Xóa gói tập thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi xóa gói tập: " + e.getMessage()));
        }
    }
} 