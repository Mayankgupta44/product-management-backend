package com.example.restAPIs_practice.Controller;

import com.example.restAPIs_practice.DTO.LoginRequestDTO;
import com.example.restAPIs_practice.DTO.LoginResponseDTO;
import com.example.restAPIs_practice.DTO.RefreshTokenRequestDTO;
import com.example.restAPIs_practice.Exception.InvalidRefreshTokenException;
import com.example.restAPIs_practice.Service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
        LoginResponseDTO loginResponse = authService.login(request, response);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@CookieValue(value = "refresh_token", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new InvalidRefreshTokenException(
                    "Refresh token is missing. Please log in again."
            );
        }

        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken, response));
    }


    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @CookieValue(value = "refresh_token", required = false)
            String refreshToken,
            HttpServletResponse response) {

        authService.logout(refreshToken, response);
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }

}