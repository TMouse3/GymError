package com.QLGym.GymError.controller;

import com.QLGym.GymError.dto.GoiTapDto;
import com.QLGym.GymError.entity.GoiTap;
import com.QLGym.GymError.service.GoiTapService;
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
    private GoiTapService goiTapService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<GoiTapDto> goiTapList = goiTapService.getAllGoiTap();
            return ResponseEntity.ok(goiTapList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi lấy danh sách gói tập: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGoiTapById(@PathVariable Integer id) {
        try {
            GoiTapDto goiTap = goiTapService.getGoiTapById(id);
            if (goiTap == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Không tìm thấy gói tập với ID: " + id));
            }
            return ResponseEntity.ok(goiTap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi lấy gói tập theo ID: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GoiTap goiTap) {
        try {
            // Assume GoiTap entity is directly used for creation for simplicity
            GoiTap savedGoiTap = goiTapService.createGoiTap(goiTap);
            return ResponseEntity.ok(savedGoiTap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi thêm gói tập: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GoiTap goiTap) {
        try {
            // Assume GoiTap entity is directly used for update for simplicity
            GoiTap updatedGoiTap = goiTapService.updateGoiTap(id, goiTap);
             if (updatedGoiTap == null) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                         .body(Map.of("message", "Không tìm thấy gói tập với ID: " + id));
             }
            return ResponseEntity.ok(updatedGoiTap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi cập nhật gói tập: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            goiTapService.deleteGoiTap(id);
            return ResponseEntity.ok(Map.of("message", "Xóa gói tập thành công"));
        } catch (RuntimeException e) {
             // Catch the specific RuntimeException from the service if not found
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body(Map.of("message", "Không tìm thấy gói tập với ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi xóa gói tập: " + e.getMessage()));
        }
    }
} 