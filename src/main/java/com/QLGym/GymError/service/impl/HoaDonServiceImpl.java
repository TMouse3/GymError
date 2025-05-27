package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.entity.GoiTap;
import com.QLGym.GymError.entity.GoiTapHoiVien;
import com.QLGym.GymError.entity.GoiTapHoiVienId;
import com.QLGym.GymError.entity.HoiVien;
import com.QLGym.GymError.entity.HoaDon;
import com.QLGym.GymError.entity.NhanVienLeTan;
import com.QLGym.GymError.repository.GoiTapHoiVienRepository;
import com.QLGym.GymError.repository.GoiTapRepository;
import com.QLGym.GymError.repository.HoiVienRepository;
import com.QLGym.GymError.repository.HoaDonRepository;
import com.QLGym.GymError.repository.NhanVienLeTanRepository;
import com.QLGym.GymError.service.HoaDonService;
import com.QLGym.GymError.dto.HoaDonDto;
import com.QLGym.GymError.dto.request.CreatePackageInvoiceRequest;
import com.QLGym.GymError.dto.request.CreateSessionInvoiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoiVienRepository hoiVienRepository;

    @Autowired
    private GoiTapRepository goiTapRepository;

    @Autowired
    private NhanVienLeTanRepository nhanVienLeTanRepository;

    @Autowired
    private GoiTapHoiVienRepository goiTapHoiVienRepository;

    @Override
    public List<HoaDonDto> getAllHoaDon() {
        return hoaDonRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HoaDon getHoaDonById(Integer id) {
        return hoaDonRepository.findById(id)
                               .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn với ID: " + id));
    }

    @Override
    @Transactional
    public HoaDon createSessionInvoice(CreateSessionInvoiceRequest request) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setTenHoaDon(request.getTenHoaDon() != null ? request.getTenHoaDon() : "Hóa đơn tập theo buổi");
        hoaDon.setGiaTien(request.getGiaTien());
        hoaDon.setNgayThanhToan(new Date());
        hoaDon.setGhiChu(request.getGhiChu());
        hoaDon.setHoiVien(null);
        hoaDon.setGoiTap(null);

        if (request.getMaNhanVienLeTan() != null) {
            Optional<NhanVienLeTan> nhanVienOpt = nhanVienLeTanRepository.findById(request.getMaNhanVienLeTan());
            nhanVienOpt.ifPresent(hoaDon::setNhanVienLeTan);
        }

        return hoaDonRepository.save(hoaDon);
    }

    @Override
    @Transactional
    public HoaDon createPackageInvoice(CreatePackageInvoiceRequest request) {
        HoiVien hoiVien = hoiVienRepository.findById(request.getMaHoiVien())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hội viên với ID: " + request.getMaHoiVien()));

        GoiTap goiTap = goiTapRepository.findById(request.getMaGoiTap())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói tập với ID: " + request.getMaGoiTap()));

        NhanVienLeTan nhanVienLeTan = nhanVienLeTanRepository.findById(request.getMaNhanVienLeTan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên với ID: " + request.getMaNhanVienLeTan()));

        HoaDon hoaDon = new HoaDon();
        hoaDon.setTenHoaDon("Hóa đơn gói tập: " + goiTap.getTenGoiTap());
        hoaDon.setGiaTien(goiTap.getGiaGoiTap());
        hoaDon.setNgayThanhToan(new Date());
        hoaDon.setGhiChu(request.getGhiChu());
        hoaDon.setHoiVien(hoiVien);
        hoaDon.setGoiTap(goiTap);
        hoaDon.setNhanVienLeTan(nhanVienLeTan);

        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        GoiTapHoiVien goiTapHoiVien = new GoiTapHoiVien();
        GoiTapHoiVienId goiTapHoiVienId = new GoiTapHoiVienId(request.getMaHoiVien(), request.getMaGoiTap());
        goiTapHoiVien.setId(goiTapHoiVienId);

        goiTapHoiVien.setHoiVien(hoiVien);
        goiTapHoiVien.setGoiTap(goiTap);
        
        Date ngayBatDau = new Date();
        goiTapHoiVien.setNgayBatDau(ngayBatDau);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(ngayBatDau);
        cal.add(Calendar.DATE, goiTap.getSoNgay());
        goiTapHoiVien.setNgayKetThuc(cal.getTime());

        goiTapHoiVienRepository.save(goiTapHoiVien);

        return savedHoaDon;
    }

    @Override
    public void deleteHoaDon(Integer id) {
        HoaDon hoaDon = hoaDonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tìm thấy với ID: " + id));
        hoaDonRepository.delete(hoaDon);
    }

    private HoaDonDto convertToDto(HoaDon hoaDon) {
        HoaDonDto dto = new HoaDonDto();
        dto.setMaHoaDon(hoaDon.getMaHoaDon());
        dto.setTenHoaDon(hoaDon.getTenHoaDon());
        dto.setNgayThanhToan(hoaDon.getNgayThanhToan());
        dto.setGiaTien(hoaDon.getGiaTien());
        dto.setGhiChu(hoaDon.getGhiChu());
        dto.setTenHoiVien(hoaDon.getHoiVien() != null ? hoaDon.getHoiVien().getHoTen() : null);
        dto.setTenGoiTap(hoaDon.getGoiTap() != null ? hoaDon.getGoiTap().getTenGoiTap() : null);
        dto.setTenNhanVien(hoaDon.getNhanVienLeTan() != null ? hoaDon.getNhanVienLeTan().getHoTen() : null);
        return dto;
    }
} 