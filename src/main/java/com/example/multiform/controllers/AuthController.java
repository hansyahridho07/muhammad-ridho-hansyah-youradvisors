package com.example.multiform.controllers;

import com.example.multiform.config.appHandler.ResponseHandler;
import com.example.multiform.data.auth.request.LoginDataRequest;
import com.example.multiform.data.auth.request.RegisterDataRequest;
import com.example.multiform.data.auth.response.LoginDataResponse;
import com.example.multiform.data.auth.response.RegisterDataResponse;
import com.example.multiform.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDataRequest request) {
        RegisterDataResponse result = authenticationService.signup(request);
        return ResponseHandler.success("Successfully register","form", result, HttpStatus.CREATED);
    }
    
    @PatchMapping("/verify/{token}")
    public ResponseEntity<Object> verify(@PathVariable("token") String id) {
        authenticationService.verify(id);
        return ResponseHandler.success("Successfully verified account","form", null, HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Object> register(@RequestBody @Valid LoginDataRequest request) {
        LoginDataResponse result = authenticationService.authenticate(request);
        return ResponseHandler.success("Successfully login","form", result, HttpStatus.OK);
    }
}