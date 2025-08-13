package com.hexaware.simplyFly.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hexaware.simplyfly.dto.BookingDto;
import com.hexaware.simplyfly.dto.FlightDto;
import com.hexaware.simplyfly.entities.Booking;
import com.hexaware.simplyfly.entities.Flight;
import com.hexaware.simplyfly.entities.Route;
import com.hexaware.simplyfly.entities.Seat;
import com.hexaware.simplyfly.entities.UserInfo;
import com.hexaware.simplyfly.services.BookingServiceImpl;
import com.hexaware.simplyfly.services.FlightServiceImpl;
import com.hexaware.simplyfly.services.RouteServiceImpl;
import com.hexaware.simplyfly.services.SeatServiceImpl;
import com.hexaware.simplyfly.services.UserInfoServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class BookingServiceImplTest {

    @Autowired private BookingServiceImpl bookingService;
    @Autowired private UserInfoServiceImpl userService;
    @Autowired private FlightServiceImpl flightService;
    @Autowired private RouteServiceImpl routeService;
    @Autowired private SeatServiceImpl seatService;

    private UserInfo ensureUser(String email) {
        UserInfo u = new UserInfo();
        u.setName("BookUser");
        u.setEmail(email);
        u.setPassword("Pass@1234");
        u.setGender("M");
        u.setContactNo("9876543210");
        u.setAddress("Addr");
        u.setRole("USER");
        return userService.addUser(u);
    }

    private Flight ensureFlightWithSeats(String seat1, String seat2) {
        Route r = new Route();
        r.setOrigin("PNQ");
        r.setDestination("GOI");
        r.setDurationMinutes(60);
        r.setDistanceKm(430.0);
        r = routeService.addRoute(r);

        FlightDto dto = new FlightDto();
        dto.setFlightName("BookFlight");
        dto.setFlightNumber("BF123");
        dto.setTotalSeats(10);
        dto.setFare(1999.0);
        dto.setBaggageInfo("15kg");
        dto.setDepartureDateTime("2025-08-13T08:00");
        dto.setArrivalDateTime("2025-08-13T09:00");
        dto.setRouteId(r.getId());
        Flight f = flightService.createFlight(dto);

        Seat s1 = new Seat(); s1.setSeatNumber(seat1); s1.setSeatClass("ECONOMY"); s1.setBooked(false); s1.setFlight(f);
        Seat s2 = new Seat(); s2.setSeatNumber(seat2); s2.setSeatClass("ECONOMY"); s2.setBooked(false); s2.setFlight(f);
        seatService.addSeat(s1); seatService.addSeat(s2);
        return f;
    }

    @Test @Order(1)
    void testCreateBooking_andGetById() {
        String email = "booking_user1@test.com";
        ensureUser(email);
        Flight f = ensureFlightWithSeats("A1", "A2");

        BookingDto dto = new BookingDto();
        dto.setFlightId(f.getId());
        dto.setSeatNumbers(List.of("A1", "A2"));

        Booking b = bookingService.createBooking(email, dto);
        assertNotNull(b.getId());

        Booking fetched = bookingService.getBookingById(b.getId());
        assertEquals("PENDING", fetched.getStatus());
        assertEquals(2, fetched.getSeats().size());
    }

    @Test @Order(2)
    void testGetBookingsForUser_andCancelBooking() {
        String email = "booking_user2@test.com";
        ensureUser(email);
        Flight f = ensureFlightWithSeats("B1", "B2");

        BookingDto dto = new BookingDto();
        dto.setFlightId(f.getId());
        dto.setSeatNumbers(List.of("B1", "B2"));
        Booking b = bookingService.createBooking(email, dto);

        List<Booking> userBookings = bookingService.getBookingsForUser(email);
        assertTrue(userBookings.stream().anyMatch(x -> x.getId().equals(b.getId())));

        bookingService.cancelBooking(email, b.getId());
        Booking after = bookingService.getBookingById(b.getId());
        assertEquals("CANCELLED", after.getStatus());
    }
}
