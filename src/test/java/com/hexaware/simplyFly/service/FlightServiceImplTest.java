package com.hexaware.simplyFly.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hexaware.simplyfly.dto.FlightDto;
import com.hexaware.simplyfly.entities.Flight;
import com.hexaware.simplyfly.entities.Route;
import com.hexaware.simplyfly.services.FlightServiceImpl;
import com.hexaware.simplyfly.services.RouteServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class FlightServiceImplTest {

    @Autowired 
    private FlightServiceImpl flightService;
    @Autowired
    private RouteServiceImpl routeService;

    private Route ensureRoute() {
        Route r = new Route();
        r.setOrigin("BLR");
        r.setDestination("DEL");
        r.setDurationMinutes(150);
        r.setDistanceKm(1740.0);
        return routeService.addRoute(r);
    }

    private FlightDto makeFlightDto(Long routeId) {
        String uniq = UUID.randomUUID().toString().substring(0,5).toUpperCase();
        FlightDto dto = new FlightDto();
        dto.setFlightName("TestFlight"+uniq);
        dto.setFlightNumber("TF" + (100 + (int)(Math.random()*900))); // e.g. TF345
        dto.setTotalSeats(10);
        dto.setFare(4999.0);
        dto.setBaggageInfo("15kg");
        dto.setDepartureDateTime("2025-08-13T10:00");
        dto.setArrivalDateTime("2025-08-13T12:30");
        dto.setRouteId(routeId);
        return dto;
    }

    @Test @Order(1)
    void testCreateFlight_andGetById() {
        Route route = ensureRoute();
        Flight created = flightService.createFlight(makeFlightDto(route.getId()));
        assertNotNull(created.getId());

        Flight fetched = flightService.getFlightById(created.getId());
        assertEquals(created.getFlightNumber(), fetched.getFlightNumber());
    }

    @Test @Order(2)
    void testGetAllFlights() {
        Route route = ensureRoute();
        flightService.createFlight(makeFlightDto(route.getId()));
        List<Flight> flights = flightService.getAllFlights();
        assertTrue(flights.size() > 0);
    }

    @Test @Order(3)
    void testUpdateFlight() {
        Route route = ensureRoute();
        Flight created = flightService.createFlight(makeFlightDto(route.getId()));

        FlightDto update = new FlightDto();
        update.setFlightName("UpdatedName");
        update.setFare(5999.0);
        update.setRouteId(route.getId());

        Flight updated = flightService.updateFlight(created.getId(), update);
        assertEquals("UpdatedName", updated.getFlightName());
        assertEquals(5999.0, updated.getFare());
    }

    @Test @Order(4)
    void testDeleteFlight() {
        Route route = ensureRoute();
        Flight created = flightService.createFlight(makeFlightDto(route.getId()));
        String msg = flightService.deleteFlight(created.getId());
        assertEquals("Flight deleted successfully", msg);
    }
}
