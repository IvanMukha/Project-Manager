package com.ivan.projectmanager.config.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtTokenService {
    String generateJwt(Authentication authentication);

    Claims getClaims(String jwt);

    boolean isValidJwt(String jwt);

    String getUsernameFromToken(String token);
}
