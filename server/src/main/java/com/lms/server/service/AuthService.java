package com.lms.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lms.server.customPayload.request.AuthRequest;
import com.lms.server.customPayload.response.AuthResponse;
import com.lms.server.model.UserPrincipal;
import com.lms.server.security.JwtService;

@Service
public class AuthService {

    @Autowired
    private UserPrincipalService userDetailsService;
    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserID(),
                        request.getPassword()));
        UserDetails user = this.userDetailsService.loadUserByUserID(Long.parseLong(request.getUserID()));
        var jwtToken = jwtService.generateToken((UserPrincipal) user);
        return AuthResponse.builder()
                .jwtToken(jwtToken)
                .build();

    }
}
