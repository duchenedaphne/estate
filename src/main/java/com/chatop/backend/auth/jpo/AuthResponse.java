package com.chatop.backend.auth.jpo;

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
public class AuthResponse {
    
    private String token;
}
