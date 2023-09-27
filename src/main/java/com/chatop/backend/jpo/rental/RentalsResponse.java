package com.chatop.backend.jpo.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@ToString
public class RentalsResponse {
    
    private List<RentalJpo> rentals;
}
