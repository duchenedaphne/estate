package com.chatop.backend.models;

import java.util.Date;

import javax.persistence.*;
import lombok.*;

@Data
@Table(name = "rentals")
@Entity 
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@ToString
public class Rental {
    
    @Id
    @Column(length = 30)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int surface;
    private int price;
    private String picture;
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner_id;
    
    private Date created_at;
    private Date updated_at;
}
