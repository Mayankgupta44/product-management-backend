package com.example.restAPIs_practice.Service;

import com.example.restAPIs_practice.Config.Security.JwtService;
import com.example.restAPIs_practice.DTO.LoginRequestDTO;
import com.example.restAPIs_practice.DTO.LoginResponseDTO;
import com.example.restAPIs_practice.Entity.RefreshToken;
import com.example.restAPIs_practice.Entity.User;
import com.example.restAPIs_practice.Exception.InvalidRefreshTokenException;
import com.example.restAPIs_practice.Repository.RefreshTokenRepository;
import com.example.restAPIs_practice.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                );

        authenticationManager.authenticate(authenticationToken);

        String accessToken  = jwtService.generateToken(request.getEmail());
        String refreshTokenValue = jwtService.generateRefreshToken();

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(refreshTokenValue);
        refreshToken.setUser(user);

        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + jwtService.getRefreshTokenExpirationTime()));

        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);

        ResponseCookie cookie = ResponseCookie
                .from("refresh_token", refreshTokenValue)
                .httpOnly(true) //JavaScript cannot do document.cookie and retrieve the refresh token
                .secure(false) //true -> browser only sends the cookie over HTTPS, but false for now
                .sameSite("Strict") //tells browser to restrict the cookie in cross-site situations
                .path("/auth") //browser sends the cookie to: /auth/... ,such as: /auth/refresh
                .maxAge(
                        jwtService.getRefreshTokenExpirationTime() / 1000
                )
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return new LoginResponseDTO("Login successful", accessToken);
    }

        public LoginResponseDTO refreshAccessToken(String refreshTokenValue, HttpServletResponse response) {
            RefreshToken refreshToken = refreshTokenRepository.findByTokenAndRevokedFalse(refreshTokenValue)
                            .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

            if (refreshToken.getExpiryDate().before(new Date())) {
                refreshToken.setRevoked(true);
                refreshTokenRepository.save(refreshToken);

                throw new InvalidRefreshTokenException("Refresh token expired");
            }

            User user = refreshToken.getUser();

            String newAccessToken = jwtService.generateToken(user.getEmail());

            String newRefreshTokenValue = jwtService.generateRefreshToken();

            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);

            RefreshToken newRefreshToken = new RefreshToken();

            newRefreshToken.setToken(newRefreshTokenValue);
            newRefreshToken.setUser(user);

            newRefreshToken.setExpiryDate(new Date(System.currentTimeMillis() + jwtService.getRefreshTokenExpirationTime()));

            newRefreshToken.setRevoked(false);

            refreshTokenRepository.save(newRefreshToken);

            // Send new refresh token as HttpOnly cookie
            ResponseCookie cookie = ResponseCookie
                    .from("refresh_token", newRefreshTokenValue)
                    .httpOnly(true)
                    .secure(false) // true in production with HTTPS
                    .sameSite("Strict")
                    .path("/auth")
                    .maxAge(jwtService.getRefreshTokenExpirationTime() / 1000)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return new LoginResponseDTO("Access token refreshed successfully", newAccessToken);
        }


    public void logout(String refreshTokenValue, HttpServletResponse response) {
        if (refreshTokenValue != null && !refreshTokenValue.isBlank()) {
            refreshTokenRepository
                    .findByTokenAndRevokedFalse(refreshTokenValue)
                    .ifPresent(refreshToken -> {
                        refreshToken.setRevoked(true);
                        refreshTokenRepository.save(refreshToken);
                    });
        }

        ResponseCookie cookie = ResponseCookie
                .from("refresh_token", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }


}
