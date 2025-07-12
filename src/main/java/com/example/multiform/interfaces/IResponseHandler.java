package com.example.multiform.interfaces;

import com.example.multiform.data.form.request.ResponseDataRequest;

public interface IResponseHandler {
    void createResponse(ResponseDataRequest request, String formId);
}
