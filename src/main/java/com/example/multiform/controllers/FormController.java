package com.example.multiform.controllers;

import com.example.multiform.config.appHandler.ResponseHandler;
import com.example.multiform.data.form.request.FormDataRequest;
import com.example.multiform.data.form.response.FormDataResponse;
import com.example.multiform.data.form.response.FormDetailResponse;
import com.example.multiform.data.general.request.PaginationParam;
import com.example.multiform.services.form.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/forms")
public class FormController {
    private final FormService formService;
    
    @PostMapping()
    public ResponseEntity<Object> register(@RequestBody @Valid FormDataRequest request) {
        FormDataResponse result = formService.createForm(request);
        return ResponseHandler.success("Successfully create form","form", result, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<Object> getAllForms(PaginationParam param) {
        List<FormDataResponse> results = formService.findAllForms(param);
        return ResponseHandler.success("Get all forms success","form", results, HttpStatus.CREATED);
    }
    
    @GetMapping("/{form_slug}")
    public ResponseEntity<Object> getDetail(@PathVariable("form_slug") String id) {
        FormDetailResponse results = formService.getFormDetail(id);
        return ResponseHandler.success("Get form success","form", results, HttpStatus.OK);
    }
}
