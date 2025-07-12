package com.example.multiform.services.form;

import com.example.multiform.data.form.request.ResponseDataRequest;
import com.example.multiform.data.form.response.ResponseDataResponse;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    
    @Override
    @Transactional
    public List<ResponseDataResponse> toResponses(String formId) {
        FormEntity form = formRepository.findById(Long.parseLong(formId))
            .orElseThrow(() -> new NoSuchElementException("Form not found"));
        Hibernate.initialize(form.getResponses());
        List<ResponseEntity> responses = form.getResponses();
        List<ResponseDataResponse> responseDataResponses = new ArrayList<>();
        responses.forEach(response -> {
            Hibernate.initialize(response.getUser());
            UserEntity user = response.getUser();
            Hibernate.initialize(response.getAnswers());
            List<AnswerEntity> answers = response.getAnswers();
            answers.forEach(answer -> {
//                QuestionEntity questions = answer.getQuestion();
                
                ResponseDataResponse.UserResponse userResponse = new ResponseDataResponse.UserResponse()
                    .builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .emailVerifiedAt(user.getEmailVerifiedAt())
                    .build();
                
                ResponseDataResponse.AnswerResponse answerResponse = new ResponseDataResponse.AnswerResponse().builder()
                    .name("-name-")
                    .address("-address-")
                    .bornDate("-bornDate-")
                    .sex("-sex-")
                    .value(answer.getValue())
                    .build();
                
                ResponseDataResponse dataResponse = new ResponseDataResponse()
                    .builder()
                    .date(convertDateToString(response.getDate()))
                    .user(userResponse)
                    .answers(answerResponse)
                    .build();
                responseDataResponses.add(dataResponse);
            });
        });
        
        //select * from form f inner join response r on f.id = r.form_id
        return responseDataResponses;
    }
    
    private String convertDateToString(LocalDateTime now) {
//        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
