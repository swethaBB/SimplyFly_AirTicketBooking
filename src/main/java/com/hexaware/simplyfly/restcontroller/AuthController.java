package com.hexaware.simplyfly.restcontroller;

import com.hexaware.simplyfly.dto.AuthRequestDto;
import com.hexaware.simplyfly.dto.AuthResponseDto;
import com.hexaware.simplyfly.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        String email = authentication.getName();
        String role = authentication.getAuthorities()
                                    .iterator()
                                    .next()
                                    .getAuthority()
                                    .replace("ROLE_", "");
        
        String token = jwtUtil.generateToken(email, role);
        return new AuthResponseDto(token);
    }
}
