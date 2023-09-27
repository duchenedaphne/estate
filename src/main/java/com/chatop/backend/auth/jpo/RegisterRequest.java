package com.chatop.backend.auth.jpo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@ToString
public class RegisterRequest {
    
    private String name;
    private String email;
    private String password;
}
