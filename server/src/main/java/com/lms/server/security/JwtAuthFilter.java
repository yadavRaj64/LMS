package com.lms.server.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lms.server.model.UserPrincipal;
import com.lms.server.service.UserPrincipalService;

import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private JwtService jwtService;
    @Autowired
    private UserPrincipalService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        logger.debug("Request URI: {}", path);
        // Bypass authentication for permitted routes
        if (path.startsWith("/api/auth") || path.equals("/api/health") || path.equals("/api/ready")) {
            logger.debug("Allowing unauthenticated access to: {}", path);
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = getJwt(request);
        logger.debug("JWT Token: {}", jwt);

        if (jwt != null) {
            Long userID = jwtService.extractUserID(jwt);
            logger.debug("Extracted User ID: {}", userID);
            if (userID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUserID(userID);
                logger.debug("Loaded UserDetails: {}", userDetails);
                if (jwtService.isTokenValid(jwt, (UserPrincipal) userDetails)) {
                    logger.debug("JWT Token is valid");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    logger.debug("JWT Token is invalid");
                }
            } else {
                logger.debug("User ID is null or authentication is already set");
            }
        } else {
            logger.debug("JWT Token is null");
        }

        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

}
