package com.hexaware.simplyfly.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String gender;
    private String contactNo;
    private String address;
    private String role = "USER"; // USER, FLIGHT_OWNER, ADMIN
    private LocalDateTime createdAt = LocalDateTime.now();
}
