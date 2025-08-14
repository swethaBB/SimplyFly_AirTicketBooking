package com.hexaware.simplyfly.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


/*Author : Swetha 
Modified On : 25-07-2025
Description :Payment entity class 
*/

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private LocalDateTime paymentDate = LocalDateTime.now();
    private String method;
    private String status = "PENDING";
    @OneToOne
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;
}
