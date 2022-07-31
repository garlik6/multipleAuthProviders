package com.example.multipleauthproviders.security.filters;

import com.example.multipleauthproviders.entities.Otp;
import com.example.multipleauthproviders.repositories.OtpRepository;
import com.example.multipleauthproviders.security.authentifications.OtpAuthentication;
import com.example.multipleauthproviders.security.authentifications.MyUsernamePasswordAuthentication;
import com.example.multipleauthproviders.security.managers.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setOtpRepository(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    private OtpRepository otpRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Step 1: username & password
        // Step 2: username & otp

        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        Authentication a;
        if (otp == null) {
            //step 1
            a = new MyUsernamePasswordAuthentication(username, password);
            // we generate an OTP
            String code = String.valueOf(new Random().nextInt(9999) + 1000);
            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);
        } else {
            //step 2
            a = new OtpAuthentication(username, otp);
            // we issue a token
            String token = UUID.randomUUID().toString();
            tokenManager.add(token);
            response.setHeader("Authorization", token);
        }
        authenticationManager.authenticate(a);
        SecurityContextHolder.getContext().setAuthentication(a);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }

}
