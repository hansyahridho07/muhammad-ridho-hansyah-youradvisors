package com.example.multiform.mappers.form;

import com.example.multiform.data.form.request.FormDataRequest;
import com.example.multiform.data.form.response.FormDataResponse;
import com.example.multiform.data.form.response.FormDetailResponse;
import com.example.multiform.embed.QuestionDataEmbed;
import com.example.multiform.entities.form.AllowedDomainEntity;
import com.example.multiform.entities.form.FormEntity;
import com.example.multiform.entities.form.QuestionEntity;
import com.example.multiform.mappers.auth.service.AuthMapperService;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
    AuthMapperService.class
}, builder = @Builder(disableBuilder = true))
public interface IFormMapper {
    @Mapping(source = ".", target = "company", qualifiedByName = "setCompany")
    @Mapping(source = "email", target = "creator", qualifiedByName = "setUser")
    @Mapping(target = "allowedDomains", ignore = true)
    @Mapping(target = "limitOneResponse", qualifiedByName = "setBooleanToInteger")
    FormEntity toFormEntity(FormDataRequest form);
    
    @Mapping(target = "limitOneResponse", qualifiedByName = "setIntegerToBoolean")
    @Mapping(source = "creator.id", target = "creatorId")
    FormDataResponse toFormDataResponse(FormEntity form);
    
    @Mapping(target = "limitOneResponse", qualifiedByName = "setIntegerToBoolean")
    @Mapping(target = "questions")
    @Mapping(target = "allowedDomains", qualifiedByName = "setArrayAllowedDomainsToArrayOfString")
    @Mapping(source = "creator.id", target = "creatorId")
    FormDetailResponse toFormDetailResponse(FormEntity form);
    
    @Mapping(source = "form.id", target = "formId")
    QuestionDataEmbed toQuestionDataEmbed(QuestionEntity question);
}
