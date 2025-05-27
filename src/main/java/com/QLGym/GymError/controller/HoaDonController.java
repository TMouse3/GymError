package com.QLGym.GymError.controller;

import com.QLGym.GymError.dto.HoaDonDto;
import com.QLGym.GymError.entity.HoaDon;
import com.QLGym.GymError.service.HoaDonService;
import com.QLGym.GymError.dto.request.CreatePackageInvoiceRequest;
import com.QLGym.GymError.dto.request.CreateSessionInvoiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping
    public ResponseEntity<?> getAllHoaDon() {
        try {
            List<HoaDonDto> hoaDons = hoaDonService.getAllHoaDon();
            return ResponseEntity.ok(hoaDons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi lấy danh sách hóa đơn: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public HoaDon getHoaDonById(@PathVariable Integer id) {
        return hoaDonService.getHoaDonById(id);
    }

    @PostMapping("/session")
    public ResponseEntity<?> createSessionInvoice(@RequestBody CreateSessionInvoiceRequest request) {
         try {
            HoaDon newHoaDon = hoaDonService.createSessionInvoice(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newHoaDon);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Lỗi khi tạo hóa đơn buổi tập: " + e.getMessage()));
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi hệ thống khi tạo hóa đơn buổi tập: " + e.getMessage()));
        }
    }

    @PostMapping("/package")
    public ResponseEntity<?> createPackageInvoice(@RequestBody CreatePackageInvoiceRequest request) {
        try {
            HoaDon newHoaDon = hoaDonService.createPackageInvoice(request);
             return ResponseEntity.status(HttpStatus.CREATED).body(newHoaDon);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Lỗi khi tạo hóa đơn gói tập: " + e.getMessage()));
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi hệ thống khi tạo hóa đơn gói tập: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            hoaDonService.deleteHoaDon(id);
            return ResponseEntity.ok(Map.of("message", "Xóa hóa đơn thành công"));
        } catch (RuntimeException e) {
            // Catch the specific RuntimeException from the service if not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Không tìm thấy hóa đơn với ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Lỗi khi xóa hóa đơn: " + e.getMessage()));
        }
    }
} 