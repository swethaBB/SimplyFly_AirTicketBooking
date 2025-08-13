package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.dto.UserInfoDto;
import com.hexaware.simplyfly.entities.UserInfo;
import com.hexaware.simplyfly.services.IUserInfoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserInfoRestController {
	
    @Autowired
    private IUserInfoService service;

    public UserInfoRestController(IUserInfoService service) { this.service = service; }

    @PostMapping("/adduser")
    public UserInfo addUser(@RequestBody UserInfo user) {
    	log.info("Adding new user with email: {}", user.getEmail());
        return service.addUser(user);
    }

    @GetMapping("/{id}")
    public UserInfo getUserById(@PathVariable Long id) {
    	log.info("Fetching user details for ID: {}", id);
        return service.getUserById(id);
    }
    
    @PostMapping("/register")
    public UserInfo register(@Valid @RequestBody UserInfoDto dto) {
        UserInfo user = new UserInfo();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setGender(dto.getGender());
        user.setContactNo(dto.getContactNo());
        user.setAddress(dto.getAddress());
        user.setRole(dto.getRole());

        return service.addUser(user);
    }


	@GetMapping("/getall")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserInfo> getAllUsers() {
    	log.info("Fetching all users");
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public UserInfo updateUser(@PathVariable Long id, @RequestBody UserInfo user) {
    	log.info("Updating user with ID: {}", id);
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
    	log.info("Deleting user with ID: {}", id);
        return service.deleteUser(id);
    }
}
