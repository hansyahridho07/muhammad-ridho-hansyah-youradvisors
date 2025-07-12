package com.example.multiform.controllers;

import com.example.multiform.config.appHandler.ResponseHandler;
import com.example.multiform.data.form.request.FormDataRequest;
import com.example.multiform.data.form.request.QuestionDataRequest;
import com.example.multiform.data.form.response.FormDataResponse;
import com.example.multiform.data.form.response.QuestionDataResponse;
import com.example.multiform.services.form.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/forms")
public class QuestionController {
    final QuestionService questionService;
    @PostMapping("/{form_slug}/questions")
    public ResponseEntity<Object> register(@RequestBody @Valid QuestionDataRequest request, @PathVariable("form_slug") String id) {
        QuestionDataResponse result = questionService.createQuestion(request, id);
        return ResponseHandler.success("Add question success","question", result, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{form_slug}/questions/{question_id}")
    public ResponseEntity<Object> removeQuestion(@PathVariable("form_slug") String formId, @PathVariable("question_id") String questionId) {
        questionService.removeQuestion(formId, questionId);
        return ResponseHandler.success("Remove question success","question", null, HttpStatus.OK);
    }
}
