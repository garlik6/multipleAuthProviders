package com.example.multipleauthproviders.config;

import com.example.multipleauthproviders.security.filters.TokenAuthenticationFilter;
import com.example.multipleauthproviders.security.filters.UsernamePasswordAuthFilter;
import com.example.multipleauthproviders.security.providers.OtpAuthenticationProvider;
import com.example.multipleauthproviders.security.providers.TokenAuthProvider;
import com.example.multipleauthproviders.security.providers.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfig {

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(otpAuthenticationProvider(), usernamePasswordAuthenticationProvider(), tokenAuthProvider());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationManager(authenticationManager());
        http.addFilterAt(authFilter(), BasicAuthenticationFilter.class);
        http.addFilterAfter(tokenAuthenticationFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UsernamePasswordAuthFilter authFilter() {
        return new UsernamePasswordAuthFilter();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter();
    }

    @Bean
    public AuthenticationProvider otpAuthenticationProvider(){
        return new OtpAuthenticationProvider();
    }

    @Bean
    public TokenAuthProvider tokenAuthProvider(){
        return new TokenAuthProvider();
    }

    @Bean
    public AuthenticationProvider usernamePasswordAuthenticationProvider(){
        return new UsernamePasswordAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public InitializingBean initializingBean(){
        return () -> {
            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        };
    }
}
