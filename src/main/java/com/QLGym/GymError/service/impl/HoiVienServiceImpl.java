package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.dto.request.RegisterRequest;
import com.QLGym.GymError.dto.response.RegisterResponse;
import com.QLGym.GymError.entity.HoiVien;
import com.QLGym.GymError.entity.GoiTapHoiVien;
import com.QLGym.GymError.repository.HoiVienRepository;
import com.QLGym.GymError.service.HoiVienService;
import com.QLGym.GymError.dto.HoiVienDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class HoiVienServiceImpl implements HoiVienService {
    @Autowired
    private HoiVienRepository hoiVienRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse registerHoiVien(RegisterRequest request) {
        // Kiểm tra trùng tài khoản
        if (hoiVienRepository.findByTaiKhoan(request.getTaiKhoan()).isPresent()) {
            return new RegisterResponse(false, "Tên đăng nhập đã tồn tại!");
        }
        // Kiểm tra trùng email
        if (hoiVienRepository.findByEmail(request.getEmail()).isPresent()) {
            return new RegisterResponse(false, "Email đã được sử dụng!");
        }
        // Kiểm tra trùng CCCD
        if (request.getCccd() != null && !request.getCccd().isEmpty() && hoiVienRepository.findByCccd(request.getCccd()).isPresent()) {
            return new RegisterResponse(false, "CCCD đã được sử dụng!");
        }
        // Kiểm tra xác nhận mật khẩu
        if (!request.getMatKhau().equals(request.getConfirmPassword())) {
            return new RegisterResponse(false, "Mật khẩu xác nhận không khớp!");
        }
        // Parse ngày sinh
        Date ngaySinh = null;
        try {
            if (request.getNgaySinh() != null && !request.getNgaySinh().isEmpty()) {
                ngaySinh = new SimpleDateFormat("yyyy-MM-dd").parse(request.getNgaySinh());
            }
        } catch (ParseException e) {
            return new RegisterResponse(false, "Ngày sinh không hợp lệ!");
        }
        // Tạo hội viên mới
        HoiVien hoiVien = new HoiVien();
        hoiVien.setHoTen(request.getHoTen());
        hoiVien.setGioiTinh(request.getGioiTinh());
        hoiVien.setNgaySinh(ngaySinh);
        hoiVien.setSdt(request.getSdt());
        hoiVien.setEmail(request.getEmail());
        hoiVien.setCccd(request.getCccd());
        hoiVien.setTaiKhoan(request.getTaiKhoan());
        hoiVien.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        hoiVienRepository.save(hoiVien);
        return new RegisterResponse(true, "Đăng ký thành công!");
    }

    @Override
    public List<HoiVienDto> getAllHoiVien() {
        return hoiVienRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HoiVienDto getHoiVienById(Integer id) {
        Optional<HoiVien> hoiVienOptional = hoiVienRepository.findById(id);
        if (hoiVienOptional.isPresent()) {
            return convertToDto(hoiVienOptional.get());
        } else {
            return null;
        }
    }

    @Override
    public HoiVien addHoiVien(HoiVien hoiVien) {
        // Mã hóa mật khẩu trước khi lưu
        hoiVien.setMatKhau(passwordEncoder.encode(hoiVien.getMatKhau()));
        return hoiVienRepository.save(hoiVien);
    }

    @Override
    @Transactional // Đảm bảo cả việc tìm và lưu đều thành công hoặc thất bại cùng nhau
    public HoiVien updateHoiVien(Integer id, HoiVien hoiVienDetails) {
        HoiVien hoiVien = hoiVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hội viên không tìm thấy với id " + id));

        // Cập nhật thông tin, bỏ qua các trường không được phép sửa (như tài khoản, mã hội viên)
        hoiVien.setHoTen(hoiVienDetails.getHoTen());
        hoiVien.setGioiTinh(hoiVienDetails.getGioiTinh());
        hoiVien.setNgaySinh(hoiVienDetails.getNgaySinh());
        hoiVien.setSdt(hoiVienDetails.getSdt());
        hoiVien.setEmail(hoiVienDetails.getEmail());
        hoiVien.setCccd(hoiVienDetails.getCccd());
        
        // Chỉ cập nhật mật khẩu nếu mật khẩu mới được cung cấp
        if (hoiVienDetails.getMatKhau() != null && !hoiVienDetails.getMatKhau().isEmpty()) {
            hoiVien.setMatKhau(passwordEncoder.encode(hoiVienDetails.getMatKhau()));
        }

        return hoiVienRepository.save(hoiVien);
    }

    @Override
    public void deleteHoiVien(Integer id) {
        HoiVien hoiVien = hoiVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hội viên không tìm thấy với id " + id));
        hoiVienRepository.delete(hoiVien);
    }

     private HoiVienDto convertToDto(HoiVien hoiVien) {
        HoiVienDto dto = new HoiVienDto();
        dto.setMaHoiVien(hoiVien.getMaHoiVien());
        dto.setHoTen(hoiVien.getHoTen());
        dto.setGioiTinh(hoiVien.getGioiTinh());
        // Handle date conversion if needed, or return as String if entity stores it as String
        dto.setNgaySinh(hoiVien.getNgaySinh() != null ? new SimpleDateFormat("yyyy-MM-dd").format(hoiVien.getNgaySinh()) : null); // Convert Date to yyyy-MM-dd String
        dto.setSdt(hoiVien.getSdt());
        dto.setEmail(hoiVien.getEmail());
        dto.setCccd(hoiVien.getCccd());
        dto.setTaiKhoan(hoiVien.getTaiKhoan());
        // Do not include password in DTO

        // Find the active package with the latest end date
        GoiTapHoiVien latestActivePackage = null;
        Date currentDate = new Date();

        if (hoiVien.getGoiTapHoiViens() != null) {
            for (GoiTapHoiVien gthv : hoiVien.getGoiTapHoiViens()) {
                if (gthv.getNgayKetThuc() != null && gthv.getNgayKetThuc().after(currentDate)) {
                    if (latestActivePackage == null || gthv.getNgayKetThuc().after(latestActivePackage.getNgayKetThuc())) {
                        latestActivePackage = gthv;
                    }
                }
            }
        }

        if (latestActivePackage != null) {
            dto.setActivePackageName(latestActivePackage.getGoiTap() != null ? latestActivePackage.getGoiTap().getTenGoiTap() : "Không rõ gói tập");
            dto.setActivePackageEndDate(latestActivePackage.getNgayKetThuc() != null ? new SimpleDateFormat("yyyy-MM-dd").format(latestActivePackage.getNgayKetThuc()) : "Không rõ ngày");
        } else {
            dto.setActivePackageName("Không khả dụng");
            dto.setActivePackageEndDate(""); // Set to empty string if not applicable
        }

        return dto;
    }
} 