package com.QLGym.GymError.service.impl;

import com.QLGym.GymError.dto.GoiTapDto;
import com.QLGym.GymError.entity.GoiTap;
import com.QLGym.GymError.repository.GoiTapRepository;
import com.QLGym.GymError.service.GoiTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoiTapServiceImpl implements GoiTapService {

    @Autowired
    private GoiTapRepository goiTapRepository;

    @Override
    public List<GoiTapDto> getAllGoiTap() {
        return goiTapRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GoiTapDto getGoiTapById(Integer id) {
        Optional<GoiTap> goiTapOptional = goiTapRepository.findById(id);
        return goiTapOptional.map(this::convertToDto).orElse(null);
    }

    @Override
    public GoiTap createGoiTap(GoiTap goiTap) {
        return goiTapRepository.save(goiTap);
    }

    @Override
    public GoiTap updateGoiTap(Integer id, GoiTap goiTapDetails) {
        GoiTap goiTap = goiTapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gói tập không tìm thấy với id " + id));

        goiTap.setTenGoiTap(goiTapDetails.getTenGoiTap());
        goiTap.setSoNgay(goiTapDetails.getSoNgay());
        goiTap.setGiaGoiTap(goiTapDetails.getGiaGoiTap());
        goiTap.setTrangThaiGoiTap(goiTapDetails.getTrangThaiGoiTap());
        goiTap.setMoTaGoiTap(goiTapDetails.getMoTaGoiTap());

        return goiTapRepository.save(goiTap);
    }

    @Override
    public void deleteGoiTap(Integer id) {
        GoiTap goiTap = goiTapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gói tập không tìm thấy với id " + id));
        goiTapRepository.delete(goiTap);
    }

    private GoiTapDto convertToDto(GoiTap goiTap) {
        GoiTapDto dto = new GoiTapDto();
        dto.setMaGoiTap(goiTap.getMaGoiTap());
        dto.setTenGoiTap(goiTap.getTenGoiTap());
        dto.setSoNgay(goiTap.getSoNgay());
        dto.setGiaGoiTap(goiTap.getGiaGoiTap());
        dto.setTrangThaiGoiTap(goiTap.getTrangThaiGoiTap());
        dto.setMoTaGoiTap(goiTap.getMoTaGoiTap());
        return dto;
    }
} 