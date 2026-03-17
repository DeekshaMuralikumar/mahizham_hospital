package com.hospitalmanagement.app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            authHeader = request.getHeader("authorization");
        }

        System.out.println("--- Request: " + request.getMethod() + " " + request.getRequestURI() + " ---");
        System.out.println("Auth Header Present: " + (authHeader != null));

        String email = null;
        String jwt = null;

        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ")) {
            jwt = authHeader.substring(7).trim();
            try {
                email = jwtUtils.extractEmail(jwt);
                System.out.println("Extracted Email from JWT: " + email);
            } catch (Exception e) {
                System.err.println("JWT Extraction Failed: " + e.getMessage());
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // In a real app, we would load user details from DB here.
            // For simplicity, we just create an auth token if the JWT is valid.
            if (jwtUtils.validateToken(jwt, email)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email, null, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("User Authenticated: " + email);
            } else {
                System.out.println("JWT Validation Failed for: " + email);
            }
        }
        filterChain.doFilter(request, response);
    }
}
