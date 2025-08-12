package com.hexaware.simplyfly.services;

import com.hexaware.simplyfly.entities.Route;
import com.hexaware.simplyfly.exceptions.RouteNotFoundException;
import com.hexaware.simplyfly.repositories.RouteRepository;
import com.hexaware.simplyfly.services.IRouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements IRouteService {
	
    @Autowired
    private RouteRepository repo;

    public RouteServiceImpl(RouteRepository repo) {
    	this.repo = repo;
    	}

    @Override
    public Route addRoute(Route route) {
        return repo.save(route);
    }

    @Override
    public Route getRouteById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RouteNotFoundException("Route with id " + id + " not found"));
    }

    @Override
    public List<Route> getAllRoutes() {
        return repo.findAll();
    }

    @Override
    public Route updateRoute(Long id, Route route) {
        Route existing = getRouteById(id);
        existing.setOrigin(route.getOrigin());
        existing.setDestination(route.getDestination());
        existing.setDurationMinutes(route.getDurationMinutes());
        existing.setDistanceKm(route.getDistanceKm());
        return repo.save(existing);
    }

    @Override
    public String deleteRoute(Long id) {
        Route ex = getRouteById(id);
        repo.delete(ex);
        return "Route deleted successfully";
    }
}
