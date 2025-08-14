package com.hexaware.simplyfly.services;

import com.hexaware.simplyfly.dto.PaymentDto;
import com.hexaware.simplyfly.entities.Booking;
import com.hexaware.simplyfly.entities.Payment;
import com.hexaware.simplyfly.exceptions.*;
import com.hexaware.simplyfly.repositories.BookingRepository;
import com.hexaware.simplyfly.repositories.PaymentRepository;
import com.hexaware.simplyfly.services.IPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements IPaymentService {
    
	@Autowired
    private PaymentRepository paymentRepo;
	@Autowired
    private BookingRepository bookingRepo;

    public PaymentServiceImpl(PaymentRepository paymentRepo, BookingRepository bookingRepo) {
        this.paymentRepo = paymentRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    @Transactional
    public Payment pay(PaymentDto dto) {
        Booking booking = bookingRepo.findById(dto.getBookingId()).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if (!"PENDING".equalsIgnoreCase(booking.getStatus())) {
        }
        Payment p = new Payment();
        p.setBooking(booking);
        p.setAmount(dto.getAmount());
        p.setMethod(dto.getMethod());
        p.setStatus("SUCCESS");
        Payment saved = paymentRepo.save(p);
        booking.setStatus("CONFIRMED");
        bookingRepo.save(booking);
        return saved;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepo.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
    }

    @Override
    public void refund(Long paymentId) {
        Payment p = paymentRepo.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
        p.setStatus("REFUNDED");
        paymentRepo.save(p);
    }
    
    @Override
    public String initiatePayment(Long bookingId, Double amount) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (!"PENDING".equalsIgnoreCase(booking.getStatus())) {
            throw new BadRequestException("Payment can only be initiated for pending bookings");
        }
        return "https://mockpayment.com/pay?bookingId=" + bookingId + "&amount=" + amount;
    }

}

