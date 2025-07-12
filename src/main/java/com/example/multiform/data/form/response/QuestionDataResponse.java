package com.example.multiform.data.form.response;

import com.example.multiform.enums.QuestionChoiceEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDataResponse {
    private Long id;
    
    @JsonProperty("form_id")
    private Long formId;
    
    private String name;
    
    @JsonProperty("choice_type")
    private QuestionChoiceEnum choiceType;
    
    private String choices;
    
    @JsonProperty("is_required")
    private Integer isRequired;
}
