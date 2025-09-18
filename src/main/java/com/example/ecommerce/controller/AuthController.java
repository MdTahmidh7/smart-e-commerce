package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthenticationRequest;
import com.example.ecommerce.dto.AuthenticationResponse;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public String register(
            @ModelAttribute RegisterRequest request,
            Model model
    ) {
        try {
            AuthenticationResponse response = authenticationService.register(request);
            model.addAttribute("token", response.getToken());
            model.addAttribute("message", "Registration successful!");
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            model.addAttribute("token", null);
        }
        return "home";
    }

    @PostMapping("/authenticate")
    public String authenticate(
            @ModelAttribute AuthenticationRequest request,
            Model model
    ) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            model.addAttribute("token", response.getToken());
            model.addAttribute("message", "Authentication successful!");
        } catch (Exception e) {
            model.addAttribute("error", "Authentication failed: " + e.getMessage());
            model.addAttribute("token", null);
        }
        return "home";
    }
}
