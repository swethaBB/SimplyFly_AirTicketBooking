package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.dto.BookingDto;
import com.hexaware.simplyfly.entities.Booking;
import com.hexaware.simplyfly.services.IBookingService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    private final IBookingService service;
    public BookingRestController(IBookingService service) { this.service = service; }

    @PostMapping
    public Booking createBooking(Authentication auth, @Valid @RequestBody BookingDto dto) {
        String email = (String) auth.getPrincipal();
        return service.createBooking(email, dto);
    }

    @GetMapping("/{id}")
    public Booking get(@PathVariable Long id) { return service.getBookingById(id); }

    @GetMapping("/me")
    public List<Booking> myBookings(Authentication auth) {
        String email = (String) auth.getPrincipal();
        return service.getBookingsForUser(email);
    }

    @GetMapping("/flight/{flightId}")
    public List<Booking> forFlight(@PathVariable Long flightId) {
        return service.getBookingsForFlight(flightId);
    }

    @DeleteMapping("/{id}")
    public void cancel(Authentication auth, @PathVariable Long id) {
        String email = (String) auth.getPrincipal();
        service.cancelBooking(email, id);
    }
}
