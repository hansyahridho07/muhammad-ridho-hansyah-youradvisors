package com.example.multiform.services.form;

import com.example.multiform.data.form.request.QuestionDataRequest;
import com.example.multiform.data.form.response.QuestionDataResponse;
import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.entities.form.FormEntity;
import com.example.multiform.entities.form.QuestionEntity;
import com.example.multiform.enums.QuestionChoiceEnum;
import com.example.multiform.helpers.GetDataUser;
import com.example.multiform.interfaces.IQuestionHandler;
import com.example.multiform.mappers.form.IQuestionMapper;
import com.example.multiform.repositories.form.FormRepository;
import com.example.multiform.repositories.form.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionHandler {
    final QuestionRepository questionRepository;
    final FormRepository formRepository;
    final IQuestionMapper questionMapper;
    
    @Override
    public QuestionDataResponse createQuestion(QuestionDataRequest request, String formId) {
        QuestionChoiceEnum dataEnum = QuestionChoiceEnum.fromStringOrThrow(request.getChoiceType());
        if(!isRequiredChoices(dataEnum) && request.getChoices().isEmpty()){
            throw new NoSuchElementException("Property choices is required if you chose 'multiple choice', 'dropdown', 'checkboxes'");
        }
        FormEntity form = formRepository.findById(Long.parseLong(formId))
            .orElseThrow(() -> new NoSuchElementException("Form not found"));
        
        QuestionEntity question = questionMapper.questionToQuestionEntity(request);
        question.setForm(form);
        QuestionEntity newQuestion = questionRepository.save(question);
        return questionMapper.questionEntityToQuestionDataResponse(newQuestion);
    }
    
    @Override
    @Transactional
    public void removeQuestion(String formId, String questionId) {
        QuestionEntity question = questionRepository.findByIdAndFormId(
            Long.parseLong(questionId), Integer.parseInt(formId))
            .orElseThrow(() -> new NoSuchElementException("Question not found"));
        UserEntity accountUser = GetDataUser.getUser();
        Hibernate.initialize(question.getForm());
        Hibernate.initialize(question.getForm().getCreator());
        if(!question.getForm().getCreator().getId().equals(accountUser.getId())){
            throw new NoSuchElementException("You do not have permission to delete this question");
        }
        questionRepository.delete(question);
    }
    
    private Boolean isRequiredChoices(QuestionChoiceEnum choice){
        return Arrays.asList(
            QuestionChoiceEnum.MULTIPLE_CHOICE, QuestionChoiceEnum.DROPDOWN,
            QuestionChoiceEnum.CHECKBOXES
        ).contains(choice);
    }
}
