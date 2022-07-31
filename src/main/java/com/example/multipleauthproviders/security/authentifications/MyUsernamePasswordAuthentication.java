package com.example.multipleauthproviders.security.authentifications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyUsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {
    public MyUsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MyUsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
