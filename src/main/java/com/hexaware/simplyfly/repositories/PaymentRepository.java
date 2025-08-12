package com.hexaware.simplyfly.repositories;

import com.hexaware.simplyfly.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
}
