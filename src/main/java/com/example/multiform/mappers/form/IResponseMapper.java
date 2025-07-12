package com.example.multiform.mappers.form;

import com.example.multiform.data.form.response.ResponseDataResponse;
import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.entities.form.AnswerEntity;
import com.example.multiform.entities.form.ResponseEntity;
import com.example.multiform.mappers.auth.service.AuthMapperService;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
    AuthMapperService.class
}, builder = @Builder(disableBuilder = true))
public interface IResponseMapper {
    
//    @Mapping(source = "user", target = "user")
//    @Mapping(target = "answers", ignore = true)
//    List<ResponseDataResponse> toResponses(List<ResponseEntity> responses);
    
    
    ResponseDataResponse.UserResponse toUserResponse(UserEntity user);
}
