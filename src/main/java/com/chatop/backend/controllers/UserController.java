package com.chatop.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.services.user.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SecurityRequirement(name = "openapi")
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;

    @Operation(summary = "Find an user.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(
        @PathVariable(name = "id") Long id
    ) {
        return userService.fetchUser(id);
    }
}
