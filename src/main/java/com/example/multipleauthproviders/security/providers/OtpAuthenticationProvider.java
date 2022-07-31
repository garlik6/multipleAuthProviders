package com.example.multipleauthproviders.security.providers;

import com.example.multipleauthproviders.repositories.OtpRepository;
import com.example.multipleauthproviders.security.authentifications.OtpAuthentication;
import com.example.multipleauthproviders.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private OtpRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String otp = (String) authentication.getCredentials();
        var o = repository.findByUsername(username);
        if (o.isPresent()){
            return new OtpAuthentication(username, otp, List.of(() -> "read"));
        } else
            throw new BadCredentialsException("Error!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(OtpAuthentication.class);
    }
}
