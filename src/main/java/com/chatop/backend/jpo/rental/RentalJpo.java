package com.chatop.backend.jpo.rental;

import java.util.Date;

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
public class RentalJpo {
    
    private Long id;
    private String name;
    private int surface;
    private int price;
    private String picture;
    private String description;
    private Long owner_id;
    private Date created_at;
    private Date updated_at;
}
