package com.ivan.projectmanager.config.security;

import com.ivan.projectmanager.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    public JwtAuthorizationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {
        String authorizationKey = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationKey != null && authorizationKey.startsWith("Bearer ")) {
            authorizationKey = authorizationKey.substring(7);
            try {
                if (jwtTokenService.isValidJwt(authorizationKey)) {
                    Claims claims = jwtTokenService.getClaims(authorizationKey);
                    String username = String.valueOf(claims.get("username"));
                    List<String> roles = claims.get("role", List.class);
                    List<GrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                logger.error(e.getMessage());
                SecurityContextHolder.getContext().setAuthentication(null);
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }
        filterChain.doFilter(request, response);
    }

}


