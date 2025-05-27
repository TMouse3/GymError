package com.QLGym.GymError.controller;

import com.QLGym.GymError.dto.request.CheckInRequest;
import com.QLGym.GymError.dto.response.CheckInResponse;
import com.QLGym.GymError.dto.response.CheckInHistoryDto;
import com.QLGym.GymError.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check-in")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @PostMapping
    public ResponseEntity<CheckInResponse> checkIn(@RequestBody CheckInRequest request) {
        CheckInResponse response = checkInService.processCheckIn(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            // You might want to return a different status code for failure, e.g., HttpStatus.BAD_REQUEST
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping // New endpoint for history
    public ResponseEntity<List<CheckInHistoryDto>> getCheckInHistory() {
        List<CheckInHistoryDto> history = checkInService.getCheckInHistory();
        return ResponseEntity.ok(history);
    }
} 