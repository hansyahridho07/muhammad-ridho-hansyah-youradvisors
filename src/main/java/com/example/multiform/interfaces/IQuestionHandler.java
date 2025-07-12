package com.example.multiform.interfaces;

import com.example.multiform.data.form.request.QuestionDataRequest;
import com.example.multiform.data.form.response.QuestionDataResponse;

public interface IQuestionHandler {
    QuestionDataResponse createQuestion(QuestionDataRequest request, String formId);
    void removeQuestion(String formId, String questionId);
}
