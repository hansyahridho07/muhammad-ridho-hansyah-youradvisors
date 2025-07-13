package com.example.multiform.mappers.form;

import com.example.multiform.data.form.response.ResponseDataResponse;
import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.entities.form.AnswerEntity;
import com.example.multiform.entities.form.ResponseEntity;
import com.example.multiform.mappers.auth.service.AuthMapperService;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
    AuthMapperService.class
}, builder = @Builder(disableBuilder = true))
public interface IResponseMapper {
    
    ResponseDataResponse.UserResponse toUserResponse(UserEntity user);
    
    @Mapping(target = "name", constant = "-name-")
    @Mapping(target = "address", constant = "-address-")
    @Mapping(target = "bornDate", constant = "-bornDate-")
    @Mapping(target = "sex", constant = "-sex-")
    @Mapping(target = "value")
    ResponseDataResponse.AnswerResponse toAnswerResponse(AnswerEntity answer);
    
    @Mapping(source = "userResponse", target = "user")
    @Mapping(source = "answerResponse", target = "answers")
    @Mapping(source = "response.date", target = "date", qualifiedByName = "convertDateToString")
    ResponseDataResponse toResponseDataResponse(ResponseDataResponse.UserResponse userResponse, ResponseDataResponse.AnswerResponse answerResponse, ResponseEntity response);
}
