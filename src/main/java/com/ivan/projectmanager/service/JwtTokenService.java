package com.ivan.projectmanager.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {
    String generateToken(UserDetails userDetails);

    Claims getClaims(String jwt);

    boolean isValidJwt(String jwt, UserDetails userDetails);

    String getUsernameFromToken(String token);
}
