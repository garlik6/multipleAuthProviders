package com.example.multipleauthproviders.security.providers;

import com.example.multipleauthproviders.security.authentifications.TokenAuthentication;
import com.example.multipleauthproviders.security.managers.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

public class TokenAuthProvider implements AuthenticationProvider {

    @Autowired
    private TokenManager tokenManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        boolean exists = tokenManager.contains(token);

        if(exists){
            return new TokenAuthentication(token, null, null);
        } else throw  new BadCredentialsException("Error!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(TokenAuthentication.class);
    }
}
