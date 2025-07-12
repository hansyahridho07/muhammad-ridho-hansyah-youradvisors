package com.example.multiform.interfaces;

import com.example.multiform.data.form.request.FormDataRequest;
import com.example.multiform.data.form.response.FormDataResponse;
import com.example.multiform.data.form.response.FormDetailResponse;
import com.example.multiform.data.general.request.PaginationParam;

import java.util.List;

public interface IFormHandler {
    FormDataResponse createForm(FormDataRequest formDataRequest);
    
    List<FormDataResponse> findAllForms(PaginationParam param);
    
    FormDetailResponse getFormDetail(String formId);
}
