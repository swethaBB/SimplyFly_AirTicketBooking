package com.hexaware.simplyfly.repositories;

import com.hexaware.simplyfly.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


/*Author : Swetha 
Modified On : 27-07-2025
Description : Payment Repository interface 
*/

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
}
