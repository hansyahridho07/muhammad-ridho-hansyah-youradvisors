package com.example.multiform.embed;

import com.example.multiform.enums.QuestionChoiceEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDataEmbed {
    private Long id;
    
    @JsonProperty("form_id")
    private Long formId;
    
    private String name;
    
    @JsonProperty("choice_type")
    private QuestionChoiceEnum choiceType;
    
    private String choices;
    
    @JsonProperty("is_required")
    private Boolean isRequired;
}
