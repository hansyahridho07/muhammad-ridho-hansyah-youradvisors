package com.example.multiform.controllers;

import com.example.multiform.config.appHandler.ResponseHandler;
import com.example.multiform.data.form.request.QuestionDataRequest;
import com.example.multiform.data.form.request.ResponseDataRequest;
import com.example.multiform.services.form.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/forms")
public class ResponseController {
    final ResponseService responseService;
    
    @PostMapping("/{form_slug}/responses")
    public ResponseEntity<Object> createResponse(@RequestBody @Valid ResponseDataRequest request, @PathVariable("form_slug") String id) {
        responseService.createResponse(request, id);
        return ResponseHandler.success("Submit response success","question", null, HttpStatus.CREATED);
    }
}
