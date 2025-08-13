package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.entities.Route;
import com.hexaware.simplyfly.services.IRouteService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/routes")
public class RouteRestController {
	
    @Autowired
    private IRouteService service;
    public RouteRestController(IRouteService service) { this.service = service; }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Route addRoute(@RequestBody Route route) { 
    	log.info("Adding new route from {} to {}", route.getOrigin(), route.getDestination());
        return service.addRoute(route);
        }

    @GetMapping("/{id}")
    public Route getRoute(@PathVariable Long id) { 
    	log.info("Fetching route with ID: {}", id);
    	return service.getRouteById(id); 
    }

    @GetMapping
    public List<Route> getAll() { 
    	log.info("Fetching all routes");
    	return service.getAllRoutes(); 
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Route update(@PathVariable Long id, @RequestBody Route route) {
    	log.info("Updating route with ID: {}", id);
    	return service.updateRoute(id, route); 
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
    	log.info("Deleting route with ID: {}", id);
    	return service.deleteRoute(id); 
    }
}
