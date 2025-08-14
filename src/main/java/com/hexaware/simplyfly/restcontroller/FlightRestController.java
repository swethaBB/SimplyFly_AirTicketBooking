package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.dto.FlightDto;
import com.hexaware.simplyfly.entities.Flight;
import com.hexaware.simplyfly.services.IFlightService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/flights")
public class FlightRestController {
    
	
	@Autowired
    private IFlightService service;
    public FlightRestController(IFlightService service) { 
    	this.service = service; 
    }

	/*
	 * @PostMapping
	 * 
	 * @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')") public Flight
	 * create(@RequestBody FlightDto dto) { log.info("Creating new flight: {}",
	 * dto.getFlightName()); return service.createFlight(dto); }
	 */
    
    @PostMapping("/addflight")
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public ResponseEntity<Flight> create(@Valid @RequestBody FlightDto dto) {
        log.info("Creating new flight: {}", dto.getFlightName());
        Flight flight = service.createFlight(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }

    @GetMapping("/{id}")
    public Flight get(@PathVariable Long id) {
    	log.info("Fetching flight with ID: {}", id);
    	return service.getFlightById(id); 
    }

    @GetMapping
    public List<Flight> all() { 
    	log.info("Fetching all flights");
    	return service.getAllFlights();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public Flight update(@PathVariable Long id, @RequestBody FlightDto dto) { 
    	log.info("Updating flight with ID: {}", id);
    	return service.updateFlight(id, dto); 
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) { 
    	log.info("Deleting flight with ID: {}", id);
        return service.deleteFlight(id);
        
    }

    @GetMapping("/search")
    public List<Flight> search(@RequestParam String origin, @RequestParam String destination) {
    	log.info("Searching flights from {} to {}", origin, destination);
        return service.searchFlights(origin, destination);
    }
}
