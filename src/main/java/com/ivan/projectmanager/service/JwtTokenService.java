package com.ivan.projectmanager.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtTokenService {
    String generateJwt(Authentication authentication);

    Claims getClaims(String jwt);

    boolean isValidJwt(String jwt);

    String getUsernameFromToken(String token);
}
