package com.example.multipleauthproviders.security.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenManager {
    private final Set<String> tokens = new HashSet<>();

    public void add(String token){
        tokens.add(token);
    }

    public boolean contains(String token){
        return tokens.contains(token);
    }
}
