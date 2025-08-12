package com.hexaware.simplyfly.repositories;

import com.hexaware.simplyfly.entities.Seat;
import com.hexaware.simplyfly.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightAndIsBookedFalse(Flight flight);
    Optional<Seat> findByFlightAndSeatNumber(Flight flight, String seatNumber);
    List<Seat> findByFlight(Flight f);
}
