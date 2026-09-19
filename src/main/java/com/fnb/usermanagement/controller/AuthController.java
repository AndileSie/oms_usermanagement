package com.fnb.usermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fnb.usermanagement.dto.LoginRequest;
import com.fnb.usermanagement.dto.LoginResponse;
import com.fnb.usermanagement.dto.RegisterRequest;
import com.fnb.usermanagement.dto.UserResponse;
import com.fnb.usermanagement.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping ("/api/auth/")
public class AuthController {
    

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.regitserUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    }



}
