package com.hexaware.simplyfly.repositories;

import com.hexaware.simplyfly.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> { 
	
}
