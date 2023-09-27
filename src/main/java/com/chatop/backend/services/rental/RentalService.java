package com.chatop.backend.services.rental;

import java.util.List;

import com.chatop.backend.models.Rental;

public interface RentalService {
    
    public Rental createRental(Rental rental) throws Exception;
    
    public Rental getRental(Long id) throws Exception;
    
    public Rental updateRental(Rental rental) throws Exception;

    public String deleteRental(Long id) throws Exception;

    public List<Rental> getAllRentals() throws Exception;
}
