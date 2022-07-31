package com.example.multipleauthproviders.repositories;

import com.example.multipleauthproviders.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,Integer> {
    Optional<Otp> findByUsername(String username);
}
