package com.ivan.projectmanager.config.security;

import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    private final UserRepository userRepository;
    @Value("${jwt.signing.key}")
    private String signingKey;
    @Value("${jwt.key.expiration}")
    private Long jwtExpiration;

    public JwtTokenServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateJwt(Authentication authentication) {
        User user = userRepository.getByUsernameCriteria(authentication.getName()).getFirst();
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return Jwts
                .builder()
                .subject(authentication.getName())
                .claim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(generateSecretKey())
                .compact();
    }

    public Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isValidJwt(String jwt) {
        try {
            Claims claims = getClaims(jwt);
            Date expiration = claims.getExpiration();
            if (expiration == null || expiration.before(new Date())) {
                return false;
            }
            String username = claims.getSubject();
            User user = userRepository.getByUsernameCriteria(username).getFirst();
            return user != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(signingKey.getBytes());
    }
}

