package com.example.multiform.mappers.auth;

import com.example.multiform.data.auth.request.RegisterDataRequest;
import com.example.multiform.data.auth.response.LoginDataResponse;
import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.mappers.auth.service.AuthMapperService;
import com.example.multiform.services.JwtService;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Mapper(componentModel = "spring",
    uses = {AuthMapperService.class},
    builder = @Builder(disableBuilder = true))
public interface IAuthMapper {
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(register.getPassword()))")
    @Mapping(target = "rememberToken", expression = "java(uuid.randomUUID().toString())")
    @Mapping(source = ".", target = "company", qualifiedByName = "setCompany")
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "status", constant = "ACTIVE")
    UserEntity toUser(RegisterDataRequest register, @Context PasswordEncoder passwordEncoder, @Context UUID uuid);
    
    @Mapping(target = "token")
    @Mapping(target = "expiresIn", expression = "java(jwtService.getExpirationTime())")
    LoginDataResponse toLoginDataResponse(String token, @Context JwtService jwtService);
    
}
