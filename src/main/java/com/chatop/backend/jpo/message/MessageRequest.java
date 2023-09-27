package com.chatop.backend.jpo.message;

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
public class MessageRequest {
    
    private String message;
    private Long rental_id;
    private Long user_id;
}
