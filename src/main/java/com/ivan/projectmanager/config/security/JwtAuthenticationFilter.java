package com.ivan.projectmanager.config.security;

import com.ivan.projectmanager.model.UsernamePasswordAuthentication;
import com.ivan.projectmanager.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService, UserService userService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = getBearerFromToken(request);
        if (jwt != null) {
            try {
                String username = jwtTokenService.getUsernameFromToken(jwt);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    if (jwtTokenService.isValidJwt(jwt)) {
                        UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                log.error("Unauthorized, reason: {}", e.getMessage(), e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getBearerFromToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        String jwt = null;
        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
        }
        return jwt;
    }
}

