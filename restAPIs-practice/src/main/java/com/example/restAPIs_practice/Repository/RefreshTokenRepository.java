package com.example.restAPIs_practice.Repository;

import com.example.restAPIs_practice.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByTokenAndRevokedFalse(String token); //revoked token won't be returned.
}