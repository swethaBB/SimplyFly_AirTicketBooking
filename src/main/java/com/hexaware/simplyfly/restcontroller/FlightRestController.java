package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.dto.FlightDto;
import com.hexaware.simplyfly.entities.Flight;
import com.hexaware.simplyfly.services.IFlightService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightRestController {

    private final IFlightService service;
    public FlightRestController(IFlightService service) { this.service = service; }

    @PostMapping
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public Flight create(@RequestBody FlightDto dto) { return service.createFlight(dto); }

    @GetMapping("/{id}")
    public Flight get(@PathVariable Long id) { return service.getFlightById(id); }

    @GetMapping
    public List<Flight> all() { return service.getAllFlights(); }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public Flight update(@PathVariable Long id, @RequestBody FlightDto dto) { return service.updateFlight(id, dto); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) { return service.deleteFlight(id); }

    @GetMapping("/search")
    public List<Flight> search(@RequestParam String origin, @RequestParam String destination) {
        return service.searchFlights(origin, destination);
    }
}
