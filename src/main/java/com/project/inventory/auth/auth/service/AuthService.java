package com.project.inventory.auth.auth.service;

import com.project.inventory.auth.auth.dto.LoginRequest;
import com.project.inventory.auth.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
