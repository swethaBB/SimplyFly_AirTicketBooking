package com.hexaware.simplyfly.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seats",
  uniqueConstraints = {@UniqueConstraint(columnNames = {"flight_id","seat_number"})}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seat_number", nullable = false)
    private String seatNumber;
    private String seatClass; // ECONOMY, BUSINESS, FIRST
    private boolean isBooked = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
