package com.chatop.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@Api("Rentals CRUD operations.")
@RequestMapping("/api/rentals")
public class RentalController {
    
    @Autowired
    private final RentalServiceImpl rentalService;

    @ApiOperation("Get all rentals.")
    @GetMapping
    public ResponseEntity<?> getAllRentals(
    ) {
        return rentalService.fetchListRentals();            
    }

    @ApiOperation("Get rental by id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRental(
        @PathVariable(name = "id") Long id
    ) {
        return rentalService.fetchRental(id);
    }
    
    @ApiOperation("Create rental.")
    @PostMapping(value="", consumes = {"*/*"}, produces = {"*/*"})
    public ResponseEntity<?> createRental(
        @ModelAttribute RentalRequest rentalRequest,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        return rentalService.buildRental(rentalRequest, userDetails);
    }

    @ApiOperation("Update rental.")
    @PutMapping(path ="/{id}", consumes = {"*/*"}, produces = {"*/*"})
    public ResponseEntity<?> updateRental(
        @PathVariable(name = "id") Long id,
        @ModelAttribute RentalRequest request
    ) {
        return rentalService.onChangeRental(id, request);
    }
}
