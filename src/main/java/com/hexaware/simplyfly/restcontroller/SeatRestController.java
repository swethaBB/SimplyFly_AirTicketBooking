package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.entities.Seat;
import com.hexaware.simplyfly.services.ISeatService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatRestController {

    private final ISeatService service;
    public SeatRestController(ISeatService service) { this.service = service; }

    @PostMapping
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public Seat add(@RequestBody Seat seat) { return service.addSeat(seat); }

    @GetMapping("/{id}")
    public Seat get(@PathVariable Long id) { return service.getSeatById(id); }

    @GetMapping
    public List<Seat> all() { return service.getAllSeats(); }

    @GetMapping("/flight/{flightId}/available")
    public List<Seat> available(@PathVariable Long flightId) { return service.getAvailableSeatsByFlightId(flightId); }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public Seat update(@PathVariable Long id, @RequestBody Seat seat) { return service.updateSeat(id, seat); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FLIGHT_OWNER','ADMIN')")
    public String delete(@PathVariable Long id) { return service.deleteSeat(id); }
}
