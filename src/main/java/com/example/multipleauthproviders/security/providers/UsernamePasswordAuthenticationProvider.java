package com.example.multipleauthproviders.security.providers;

import com.example.multipleauthproviders.security.authentifications.MyUsernamePasswordAuthentication;
import com.example.multipleauthproviders.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;


public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserDetails user =  userDetailsService.loadUserByUsername(username);
        String expectedPassword = user.getPassword();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        if(passwordEncoder.matches(password, expectedPassword)){
            return new MyUsernamePasswordAuthentication(username,password, authorities);
        } else {
            throw new BadCredentialsException("Error!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(MyUsernamePasswordAuthentication.class);
    }
}
