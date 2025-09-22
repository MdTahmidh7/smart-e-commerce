package com.example.ecommerce.service;

import com.example.ecommerce.dto.AuthenticationRequest;
import com.example.ecommerce.dto.AuthenticationResponse;
import com.example.ecommerce.dto.RegisterRequest;

public interface AuthService {
    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
