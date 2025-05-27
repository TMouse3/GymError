package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.dto.request.CheckInRequest;
import com.QLGym.GymError.dto.response.CheckInResponse;
import com.QLGym.GymError.dto.response.CheckInHistoryDto;
import com.QLGym.GymError.entity.CheckIn;
import com.QLGym.GymError.entity.GoiTapHoiVien;
import com.QLGym.GymError.entity.HoiVien;
import com.QLGym.GymError.entity.NhanVienLeTan;
import com.QLGym.GymError.repository.CheckInRepository;
import com.QLGym.GymError.repository.GoiTapHoiVienRepository;
import com.QLGym.GymError.repository.HoiVienRepository;
import com.QLGym.GymError.repository.NhanVienLeTanRepository;
import com.QLGym.GymError.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckInServiceImpl implements CheckInService {

    @Autowired
    private HoiVienRepository hoiVienRepository;

    @Autowired
    private NhanVienLeTanRepository nhanVienLeTanRepository;

    @Autowired
    private GoiTapHoiVienRepository goiTapHoiVienRepository;

    @Autowired
    private CheckInRepository checkInRepository;

    @Override
    @Transactional // Ensure all operations within this method are atomic
    public CheckInResponse processCheckIn(CheckInRequest request) {
        CheckInResponse response = new CheckInResponse();
        response.setSuccess(false); // Default to failure

        // 1. Find HoiVien
        Optional<HoiVien> hoiVienOpt = hoiVienRepository.findById(request.getMaHoiVien());
        if (!hoiVienOpt.isPresent()) {
            response.setMessage("Không tìm thấy hội viên với mã: " + request.getMaHoiVien());
            return response;
        }
        HoiVien hoiVien = hoiVienOpt.get();
        response.setHoTenHoiVien(hoiVien.getHoTen());

        // 2. Find NhanVienLeTan
        Optional<NhanVienLeTan> nhanVienOpt = nhanVienLeTanRepository.findById(request.getMaNhanVienLeTan());
        if (!nhanVienOpt.isPresent()) {
            response.setMessage("Không tìm thấy nhân viên lễ tân với mã: " + request.getMaNhanVienLeTan());
            return response;
        }
        NhanVienLeTan nhanVienLeTan = nhanVienOpt.get();

        // 3. Find the latest active package for the HoiVien
        // Assuming GoiTapHoiVien relationship is correctly loaded with HoiVien or can be queried directly
        // We need to find GoiTapHoiVien entries for this HoiVien where NgayKetThuc is after the current date
        Date currentDate = new Date();
        List<GoiTapHoiVien> activePackages = goiTapHoiVienRepository.findByHoiVienAndNgayKetThucAfter(hoiVien, currentDate);

        GoiTapHoiVien latestActivePackage = null;
        if (activePackages != null && !activePackages.isEmpty()) {
            latestActivePackage = activePackages.stream()
                    .max((g1, g2) -> g1.getNgayKetThuc().compareTo(g2.getNgayKetThuc()))
                    .orElse(null);
        }

        if (latestActivePackage == null) {
            response.setMessage("Hội viên không có gói tập còn khả dụng.");
            return response;
        }

        // 4. Create and Save CheckIn entity
        CheckIn checkIn = new CheckIn();
        checkIn.setHoiVien(hoiVien);
        checkIn.setNhanVienLeTan(nhanVienLeTan);
        checkIn.setNgayCheckIn(currentDate);
        // Use the BuoiCheckIn from the request DTO
        checkIn.setBuoiCheckIn(request.getBuoiCheckIn());

        CheckIn savedCheckIn = checkInRepository.save(checkIn);

        // 5. Prepare Success Response
        response.setSuccess(true);
        response.setMessage("Check-in thành công!");
        // Use savedCheckIn.getNgayCheckIn() which might have more precise time from DB
        response.setCheckInTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(savedCheckIn.getNgayCheckIn()));

        // Include package info if available
        if (latestActivePackage != null && latestActivePackage.getGoiTap() != null) {
             response.setTenGoiTap(latestActivePackage.getGoiTap().getTenGoiTap());
             if (latestActivePackage.getNgayKetThuc() != null) {
                  response.setNgayKetThucGoiTap(new SimpleDateFormat("yyyy-MM-dd").format(latestActivePackage.getNgayKetThuc()));
             }
             // SoBuoiConLai logic would go here if applicable
        }

        return response;
    }

    @Override
    public List<CheckInHistoryDto> getCheckInHistory() {
        List<CheckIn> checkins = checkInRepository.findAll();
        return checkins.stream().map(this::convertToCheckInHistoryDto).collect(Collectors.toList());
    }

    private CheckInHistoryDto convertToCheckInHistoryDto(CheckIn checkIn) {
        CheckInHistoryDto dto = new CheckInHistoryDto();
        dto.setMaCheckIn(checkIn.getMaCheckIn());
        dto.setCheckInTime(checkIn.getNgayCheckIn());
        dto.setBuoiCheckIn(checkIn.getBuoiCheckIn());
        dto.setHoTenHoiVien(checkIn.getHoiVien() != null ? checkIn.getHoiVien().getHoTen() : "N/A");
        dto.setHoTenNhanVien(checkIn.getNhanVienLeTan() != null ? checkIn.getNhanVienLeTan().getHoTen() : "N/A");
        return dto;
    }

    // We might need methods in GoiTapHoiVienRepository:
    // List<GoiTapHoiVien> findByHoiVienAndNgayKetThucAfter(HoiVien hoiVien, Date ngayKetThuc);
} 