package com.QLGym.GymError.service;

import com.QLGym.GymError.dto.request.CheckInRequest;
import com.QLGym.GymError.dto.response.CheckInResponse;
import com.QLGym.GymError.dto.response.CheckInHistoryDto;

import java.util.List;

public interface CheckInService {
    CheckInResponse processCheckIn(CheckInRequest request);
    List<CheckInHistoryDto> getCheckInHistory();
} 