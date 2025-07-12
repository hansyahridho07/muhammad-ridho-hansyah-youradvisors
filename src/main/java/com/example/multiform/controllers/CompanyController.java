package com.example.multiform.controllers;

import com.example.multiform.config.appHandler.ResponseHandler;
import com.example.multiform.data.auth.request.RegisterDataRequest;
import com.example.multiform.data.auth.response.RegisterDataResponse;
import com.example.multiform.data.company.request.CompanyDataRequest;
import com.example.multiform.services.company.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/company")
public class CompanyController {
    private final CompanyService companyService;
    
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid CompanyDataRequest request) {
        companyService.createCompany(request);
        return ResponseHandler.success("Successfully create company","form", null, HttpStatus.CREATED);
    }
}
