package com.chatop.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.backend.models.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    
}
