package com.hexaware.simplyfly.services;

import com.hexaware.simplyfly.dto.BookingDto;
import com.hexaware.simplyfly.entities.Booking;
import java.util.List;

public interface IBookingService {
    Booking createBooking(String userEmail, BookingDto dto);
    Booking getBookingById(Long id);
    List<Booking> getBookingsForUser(String userEmail);
    List<Booking> getBookingsForFlight(Long flightId);
    void cancelBooking(String userEmail, Long bookingId);

}
