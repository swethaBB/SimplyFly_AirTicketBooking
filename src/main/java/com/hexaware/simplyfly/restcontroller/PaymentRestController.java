package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.dto.PaymentDto;
import com.hexaware.simplyfly.entities.Payment;
import com.hexaware.simplyfly.services.IPaymentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

    private final IPaymentService service;
    public PaymentRestController(IPaymentService service) { this.service = service; }

    @PostMapping("/pay")
    public Payment pay(@Valid @RequestBody PaymentDto dto) {
        return service.pay(dto);
    }

    @GetMapping("/{id}")
    public Payment get(@PathVariable Long id) {
        return service.getPaymentById(id);
    }

    @PostMapping("/refund/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FLIGHT_OWNER')")
    public void refund(@PathVariable Long id) {
        service.refund(id);
    }
}
