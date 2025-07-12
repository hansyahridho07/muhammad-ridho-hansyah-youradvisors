package com.example.multiform.interfaces;

import com.example.multiform.data.form.request.ResponseDataRequest;
import com.example.multiform.data.form.response.ResponseDataResponse;

import java.util.List;

public interface IResponseHandler {
    void createResponse(ResponseDataRequest request, String formId);
    List<ResponseDataResponse> toResponses(String formId);
}
