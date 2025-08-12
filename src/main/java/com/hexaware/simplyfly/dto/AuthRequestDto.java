package com.hexaware.simplyfly.dto;
import lombok.*;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor

public class AuthRequestDto {
    public String email;
    public String password;
}
