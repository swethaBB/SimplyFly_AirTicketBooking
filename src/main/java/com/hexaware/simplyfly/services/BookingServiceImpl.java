package com.hexaware.simplyfly.services;

import com.hexaware.simplyfly.dto.BookingDto;
import com.hexaware.simplyfly.entities.*;
import com.hexaware.simplyfly.exceptions.*;
import com.hexaware.simplyfly.repositories.*;
import com.hexaware.simplyfly.services.IBookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepo;
    private final FlightRepository flightRepo;
    private final UserInfoRepository userRepo;
    private final SeatRepository seatRepo;
    private final PaymentRepository paymentRepo;

    public BookingServiceImpl(BookingRepository bookingRepo,
                              FlightRepository flightRepo,
                              UserInfoRepository userRepo,
                              SeatRepository seatRepo,
                              PaymentRepository paymentRepo) {
        this.bookingRepo = bookingRepo;
        this.flightRepo = flightRepo;
        this.userRepo = userRepo;
        this.seatRepo = seatRepo;
        this.paymentRepo = paymentRepo;
    }

    @Override
    @Transactional
    public Booking createBooking(String userEmail, BookingDto dto) {
        UserInfo user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserInfoNotFoundException("User not found"));
        Flight flight = flightRepo.findById(dto.getFlightId()).orElseThrow(() -> new FlightNotFoundException("Flight not found"));

        if (dto.getSeatNumbers() == null || dto.getSeatNumbers().isEmpty()) throw new BadRequestException("Select at least one seat");

        List<Seat> seats = dto.getSeatNumbers().stream().map(sn ->
                seatRepo.findByFlightAndSeatNumber(flight, sn).orElseThrow(() -> new SeatNotFoundException("Seat " + sn + " not found"))
        ).collect(Collectors.toList());

        for (Seat s : seats) {
            if (s.isBooked()) throw new BadRequestException("Seat " + s.getSeatNumber() + " is already booked");
        }

        Booking b = new Booking();
        b.setBookingDate(LocalDateTime.now());
        b.setFlight(flight);
        b.setUser(user);
        b.setStatus("PENDING");
        double total = flight.getFare() * seats.size();
        b.setTotalPrice(total);

        for (Seat s : seats) {
            s.setBooked(true);
            s.setBooking(b);
            b.getSeats().add(s);
        }

        Booking saved = bookingRepo.save(b);
        seatRepo.saveAll(seats);

        return saved;
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepo.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
    }

    @Override
    public List<Booking> getBookingsForUser(String userEmail) {
        UserInfo user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserInfoNotFoundException("User not found"));
        return bookingRepo.findByUser(user);
    }

    @Override
    public List<Booking> getBookingsForFlight(Long flightId) {
        return bookingRepo.findByFlight_Id(flightId);
    }

    @Override
    @Transactional
    public void cancelBooking(String userEmail, Long bookingId) {
        UserInfo user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserInfoNotFoundException("User not found"));
        Booking b = bookingRepo.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if (!b.getUser().getId().equals(user.getId())) throw new BadRequestException("Not authorized to cancel this booking");
        if ("CANCELLED".equalsIgnoreCase(b.getStatus())) throw new BadRequestException("Booking already cancelled");

        for (Seat s : b.getSeats()) {
            s.setBooked(false);
            s.setBooking(null);
        }
        seatRepo.saveAll(new ArrayList<>(b.getSeats()));
        b.setStatus("CANCELLED");
        bookingRepo.save(b);

        // refund (if payment exists)
        paymentRepo.findAll().stream()
                .filter(p -> p.getBooking() != null && p.getBooking().getId().equals(b.getId()))
                .findFirst().ifPresent(p -> { p.setStatus("REFUNDED"); paymentRepo.save(p); });
    }
}
