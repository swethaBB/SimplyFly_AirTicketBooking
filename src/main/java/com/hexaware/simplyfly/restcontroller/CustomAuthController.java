package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.dto.AuthRequestDto;
import com.hexaware.simplyfly.dto.AuthResponseDto;
import com.hexaware.simplyfly.services.IUserInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/custom-auth")
public class CustomAuthController {

    private final IUserInfoService userService;

    public CustomAuthController(IUserInfoService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        String token = userService.loginAndGetToken(request.getEmail(), request.getPassword());
        return new AuthResponseDto(token);
    }
}
