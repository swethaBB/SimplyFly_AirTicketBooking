package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.entities.Route;
import com.hexaware.simplyfly.services.IRouteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteRestController {

    private final IRouteService service;
    public RouteRestController(IRouteService service) { this.service = service; }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Route addRoute(@RequestBody Route route) { return service.addRoute(route); }

    @GetMapping("/{id}")
    public Route getRoute(@PathVariable Long id) { return service.getRouteById(id); }

    @GetMapping
    public List<Route> getAll() { return service.getAllRoutes(); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Route update(@PathVariable Long id, @RequestBody Route route) { return service.updateRoute(id, route); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) { return service.deleteRoute(id); }
}
