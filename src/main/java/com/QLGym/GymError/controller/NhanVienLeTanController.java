package com.QLGym.GymError.controller;

import com.QLGym.GymError.entity.NhanVienLeTan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.QLGym.GymError.service.NhanVienLeTanService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nhan-vien-le-tan")
public class NhanVienLeTanController {
    @Autowired
    private NhanVienLeTanService nhanVienLeTanService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<NhanVienLeTan> nhanVienList = nhanVienLeTanService.getAllNhanVienLeTan();
            return ResponseEntity.ok(nhanVienList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi lấy danh sách nhân viên: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NhanVienLeTan nv) {
        try {
            NhanVienLeTan savedNv = nhanVienLeTanService.createNhanVienLeTan(nv);
            return ResponseEntity.ok(savedNv);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi thêm nhân viên: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody NhanVienLeTan nv) {
        try {
            NhanVienLeTan updatedNv = nhanVienLeTanService.updateNhanVienLeTan(id, nv);
            if (updatedNv != null) {
                return ResponseEntity.ok(updatedNv);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Không tìm thấy nhân viên với ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi cập nhật nhân viên: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            nhanVienLeTanService.deleteNhanVienLeTan(id);
            return ResponseEntity.ok(Map.of("message", "Xóa nhân viên thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi xóa nhân viên: " + e.getMessage()));
        }
    }
} 