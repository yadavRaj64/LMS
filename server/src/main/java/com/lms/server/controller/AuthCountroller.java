package com.lms.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.server.customPayload.request.AuthRequest;
import com.lms.server.customPayload.response.AuthResponse;
import com.lms.server.service.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthCountroller {

    @Autowired
    private AuthService service;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(service.authenticate(authRequest));
    }
}
