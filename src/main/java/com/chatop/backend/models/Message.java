package com.chatop.backend.models;

import java.util.Date;

import javax.persistence.*;
import lombok.*;

@Data
@Table(name = "messages")
@Entity 
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor 
@ToString
public class Message {

    @Id 
    @Column(length = 30)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental_id;

    private Date created_at;
    private Date updated_at;
}
