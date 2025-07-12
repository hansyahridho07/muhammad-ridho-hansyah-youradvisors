package com.example.multiform.interfaces;

import com.example.multiform.data.auth.request.LoginDataRequest;
import com.example.multiform.data.auth.request.RegisterDataRequest;
import com.example.multiform.data.auth.response.LoginDataResponse;
import com.example.multiform.data.auth.response.RegisterDataResponse;
import com.example.multiform.entities.auth.UserEntity;

public interface IAuthenticationHandler {
    public RegisterDataResponse signup(RegisterDataRequest input);
    void verify(String rememberToken);
    LoginDataResponse authenticate(LoginDataRequest input);
}
