package com.chatop.backend.jpo.rental;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@ToString
public class RentalRequest {
    
    private String name;
    private int surface;
    private int price;
    private MultipartFile picture;
    private String description;
    private Long owner_id;
}
