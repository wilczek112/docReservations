package com.group.docReservations.services;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        if (request.getUserPrincipal() == null) {
            System.out.println("Unauthenticated user attempted to access restricted page.");
            response.sendRedirect("/index");
        } else {
            Authentication authentication = (Authentication) request.getUserPrincipal();
            String username = authentication.getName(); // Get username
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            String roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", ")); // Get roles as a comma-separated string

            System.out.println("Access denied for user: " + username + " with roles: " + roles);
            response.sendRedirect("/access-denied");
        }
    }
}
