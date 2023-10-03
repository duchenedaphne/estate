package com.chatop.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@SecurityRequirement(name = "spring_oauth")
@PreAuthorize("hasAuthority('SCOPE_read_access')")
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserServiceImpl userService;

    @Operation(description = "Get user by id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(
        @PathVariable(name = "id") Long id
    ) {
        return userService.fetchUser(id);
    }
}
