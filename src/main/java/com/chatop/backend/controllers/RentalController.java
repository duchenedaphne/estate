package com.chatop.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.backend.jpo.rental.RentalRequest;
import com.chatop.backend.services.rental.RentalServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SecurityRequirement(name = "spring_oauth")
@PreAuthorize("hasAuthority('SCOPE_read_access')")
@RequiredArgsConstructor
@RequestMapping("/api/rentals")
public class RentalController {
    
    @Autowired
    private RentalServiceImpl rentalService;

    @Operation(description = "Get all rentals.")
    @GetMapping
    public ResponseEntity<?> getAllRentals(
    ) {
        return rentalService.fetchListRentals();            
    }

    @Operation(description = "Get rental by id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRental(
        @PathVariable(name = "id") Long id
    ) {
        return rentalService.fetchRental(id);
    }
    
    @Operation(description = "Create rental.")
    @PostMapping(value="", consumes = {"*/*"}, produces = {"*/*"})
    public ResponseEntity<?> createRental(
        @ModelAttribute RentalRequest rentalRequest,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        return rentalService.buildRental(rentalRequest, userDetails);
    }

    @Operation(description = "Update rental.")
    @PutMapping(path ="/{id}", consumes = {"*/*"}, produces = {"*/*"})
    public ResponseEntity<?> updateRental(
        @PathVariable(name = "id") Long id,
        @ModelAttribute RentalRequest request
    ) {
        return rentalService.onChangeRental(id, request);
    }
}
