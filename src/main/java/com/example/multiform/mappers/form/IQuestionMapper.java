package com.example.multiform.mappers.form;

import com.example.multiform.data.form.request.QuestionDataRequest;
import com.example.multiform.data.form.response.QuestionDataResponse;
import com.example.multiform.entities.form.QuestionEntity;
import com.example.multiform.enums.QuestionChoiceEnum;
import com.example.multiform.mappers.auth.service.AuthMapperService;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
    AuthMapperService.class
}, builder = @Builder(disableBuilder = true))
public interface IQuestionMapper {
    
    @Mapping(target = "choices", qualifiedByName = "setToStringFromArray")
    @Mapping(target = "choiceType", qualifiedByName = "setChoiceType")
    QuestionEntity questionToQuestionEntity(QuestionDataRequest question);
    
    @Mapping(target = "isRequired", qualifiedByName = "setBooleanToInteger")
    QuestionDataResponse questionEntityToQuestionDataResponse(QuestionEntity question);
    
    @Named("setToStringFromArray")
    default String setToStringFromArray(List<String> choices) {
        return String.join(",", choices);
    }
    
    @Named("setChoiceType")
    default QuestionChoiceEnum setChoiceType(String choice) {
        return QuestionChoiceEnum.fromStringOrThrow(choice);
    }
}
