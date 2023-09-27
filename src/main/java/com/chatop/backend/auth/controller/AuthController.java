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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@Api("Register and Login requests.")
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private final AuthService authService;

    @ApiOperation("Register.")
    @PostMapping("/register")
    public ResponseEntity<?> register(
        @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }
    
    @ApiOperation("Login.")
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @ApiOperation("Get user logged.")
    @GetMapping("/me")
    public ResponseEntity<?> getUserApp(
        @AuthenticationPrincipal
        UserDetails userDetails
    ) {
        return authService.getUserApp(userDetails);
    }
}
