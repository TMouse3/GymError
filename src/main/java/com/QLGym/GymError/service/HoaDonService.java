package com.QLGym.GymError.service;

import com.QLGym.GymError.dto.HoaDonDto;
import com.QLGym.GymError.entity.HoaDon;
import com.QLGym.GymError.dto.request.CreatePackageInvoiceRequest;
import com.QLGym.GymError.dto.request.CreateSessionInvoiceRequest;

import java.util.List;

public interface HoaDonService {
    List<HoaDonDto> getAllHoaDon();
    HoaDon getHoaDonById(Integer id);
    HoaDon createSessionInvoice(CreateSessionInvoiceRequest request);
    HoaDon createPackageInvoice(CreatePackageInvoiceRequest request);
    void deleteHoaDon(Integer id);
} 