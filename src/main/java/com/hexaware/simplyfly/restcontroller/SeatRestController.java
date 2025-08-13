package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.entities.Seat;
import com.hexaware.simplyfly.services.ISeatService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/seats")
public class SeatRestController {
	
    @Autowired
    private ISeatService service;
    public SeatRestController(ISeatService service) { 
    	this.service = service; 
    }

    @PostMapping("/addseat")
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public Seat add(@RequestBody Seat seat) { 
    	log.info("Adding new seat: {}", seat.getSeatNumber());
    	return service.addSeat(seat); 
    }

    @GetMapping("/{id}")
    public Seat get(@PathVariable Long id) {
    	log.info("Fetching seat with ID: {}", id);
    	return service.getSeatById(id); 
    }

    @GetMapping("/getall")
    public List<Seat> all() { 
    	log.info("Fetching all seats");
    	return service.getAllSeats(); 
    }

    @GetMapping("/flight/{flightId}/available")
    public List<Seat> available(@PathVariable Long flightId) { 
    	log.info("Fetching available seats for flight ID: {}", flightId);
    	return service.getAvailableSeatsByFlightId(flightId); 
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public Seat update(@PathVariable Long id, @RequestBody Seat seat) { 
    	log.info("Updating seat with ID: {}", id);
    	return service.updateSeat(id, seat); 
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public String delete(@PathVariable Long id) { 
    	log.info("Deleting seat with ID: {}", id);
        return service.deleteSeat(id);
    }
}
