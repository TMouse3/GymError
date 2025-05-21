package com.QLGym.GymError.service;

import com.QLGym.GymError.dto.request.LoginRequest;
import com.QLGym.GymError.dto.response.LoginResponse;

public interface LoginService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
} 