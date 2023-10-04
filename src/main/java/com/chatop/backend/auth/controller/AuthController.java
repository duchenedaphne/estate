package com.chatop.backend.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.auth.jpo.LoginRequest;
import com.chatop.backend.auth.jpo.RegisterRequest;
import com.chatop.backend.auth.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private final AuthService authService;

    @Operation(summary = "Register an user.")
    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }
    
    @Operation(summary = "Log an user.")
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @Operation(summary = "Find the logged user.")
    @SecurityRequirement(name = "openapi")
    @GetMapping("/me")
    public ResponseEntity<?> getUserApp(
        @AuthenticationPrincipal
        UserDetails userDetails
    ) {
        return authService.getUserApp(userDetails);
    }
}
