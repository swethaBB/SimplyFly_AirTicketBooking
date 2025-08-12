package com.hexaware.simplyfly.services;

import com.hexaware.simplyfly.entities.Route;
import java.util.List;

public interface IRouteService {
    Route addRoute(Route route);
    Route getRouteById(Long id);
    List<Route> getAllRoutes();
    Route updateRoute(Long id, Route route);
    String deleteRoute(Long id);
}
