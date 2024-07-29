package com.tickmint.AuthService.controllers;

import com.tickmint.AuthService.dtos.SignInDTO;
import com.tickmint.AuthService.dtos.SignUpDTO;
import com.tickmint.AuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpDTO signUpDTO) throws Exception {
        return ResponseEntity.ok(authService.register(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInDTO signInDTO) throws Exception {
        return ResponseEntity.ok(authService.login(signInDTO));
    }

}
