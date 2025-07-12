package com.example.multiform.services.form;

import com.example.multiform.data.form.request.ResponseDataRequest;
import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.entities.form.AnswerEntity;
import com.example.multiform.entities.form.FormEntity;
import com.example.multiform.entities.form.QuestionEntity;
import com.example.multiform.entities.form.ResponseEntity;
import com.example.multiform.helpers.GetDataUser;
import com.example.multiform.interfaces.IResponseHandler;
import com.example.multiform.repositories.form.AnswerRepository;
import com.example.multiform.repositories.form.FormRepository;
import com.example.multiform.repositories.form.QuestionRepository;
import com.example.multiform.repositories.form.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ResponseService implements IResponseHandler {
    final ResponseRepository responseRepository;
    final AnswerRepository answerRepository;
    final FormRepository formRepository;
    final QuestionRepository questionRepository;
    
    @Override
    @Transactional
    public void createResponse(ResponseDataRequest request, String formId) {
        UserEntity accountUser = GetDataUser.getUser();
        FormEntity form = formRepository.findById(Long.parseLong(formId))
            .orElseThrow(() -> new NoSuchElementException("Form not found"));
        Hibernate.initialize(form.getAllowedDomains().size());
        List<String> allowedDomains = new ArrayList<>();
        form.getAllowedDomains().forEach(el -> allowedDomains.add(el.getDomain()));
        String userEmailDomain = accountUser.getEmail().substring(accountUser.getEmail().indexOf("@") + 1);
        if(!allowedDomains.contains(userEmailDomain)){
            throw new NoSuchElementException("Domain not allowed");
        }
        ResponseEntity response = new ResponseEntity().builder()
            .form(form)
            .user(accountUser)
            .build();
        
        ResponseEntity newResponse = responseRepository.save(response);
        
        List<AnswerEntity> answers = new ArrayList<>();
        request.getAnswers().forEach(el -> {
            QuestionEntity question = questionRepository.findById(Long.parseLong(el.getQuestionId()))
                .orElseThrow(() -> new NoSuchElementException("Question not found"));
            AnswerEntity answer = new AnswerEntity().builder()
                .question(question)
                .response(newResponse)
                .value(el.getValue())
                .build();
            answers.add(answer);
        });
        answerRepository.saveAll(answers);
    }
}
