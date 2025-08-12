package com.hexaware.simplyfly.services;

import com.hexaware.simplyfly.dto.PaymentDto;
import com.hexaware.simplyfly.entities.Payment;

public interface IPaymentService {
    Payment pay(PaymentDto dto);
    Payment getPaymentById(Long id);
    void refund(Long paymentId);
}
