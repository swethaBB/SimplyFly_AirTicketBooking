package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.entities.UserInfo;
import com.hexaware.simplyfly.services.IUserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserInfoRestController {
	
    @Autowired
    private IUserInfoService service;

    public UserInfoRestController(IUserInfoService service) { this.service = service; }

    @PostMapping("/adduser")
    public UserInfo addUser(@RequestBody UserInfo user) {
        return service.addUser(user);
    }

    @GetMapping("/{id}")
    public UserInfo getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/getall")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserInfo> getAllUsers() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public UserInfo updateUser(@PathVariable Long id, @RequestBody UserInfo user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        return service.deleteUser(id);
    }
}
