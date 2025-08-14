package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.dto.BookingDto;
import com.hexaware.simplyfly.entities.Booking;
import com.hexaware.simplyfly.services.IBookingService;
import com.hexaware.simplyfly.services.IPaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/bookings")
public class BookingRestController {
    
	@Autowired
    private IBookingService service;
	@Autowired
	IPaymentService paymentService;
	
    
    public BookingRestController(IBookingService service) { 
    	this.service = service; 
    	}

    @PostMapping("/bookingseat")
    public Map<String, Object> createBooking(Authentication auth, @Valid @RequestBody BookingDto dto) {
        String email = (String) auth.getPrincipal();
        Booking booking = service.createBooking(email, dto);
        String paymentUrl = paymentService.initiatePayment(booking.getId(), booking.getTotalPrice());
        log.info("Creating booking for user: {} on flight ID: {}", email, dto.getFlightId());
        return Map.of("booking", booking, "paymentRedirect", paymentUrl);
    }




    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = service.getBookingById(id);

        Booking dto = new Booking(
        );

        return ResponseEntity.ok(dto);
    }



    @GetMapping("/me")
    public List<Booking> myBookings(Authentication auth) {
        String email = (String) auth.getPrincipal();
        log.info("Fetching bookings for user: {}", email);
        return service.getBookingsForUser(email);
    }

    @GetMapping("/flight/{flightId}")
    public List<Booking> forFlight(@PathVariable Long flightId) {
    	log.info("Fetching bookings for flight ID: {}", flightId);
        return service.getBookingsForFlight(flightId);
    }

    @DeleteMapping("/{id}")
    public void cancel(Authentication auth, @PathVariable Long id) {
        String email = (String) auth.getPrincipal();
        log.info("Cancelling booking with ID: {} for user: {}", id, email);
        service.cancelBooking(email, id);
    }
}
