package com.example.multiform.data.form.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDataRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;
    
    @NotBlank(message = "Choice type is required")
    @JsonProperty("choice_type")
    @Pattern(regexp = "^(short answer|paragraph|date|multiple choice|dropdown|checkboxes)$",
        message = "Choice type must be one of: short answer, paragraph, date, multiple choice, dropdown, or checkboxes")
    private String choiceType;
    
    @JsonProperty("choices")
    private List<String> choices;
    
    @JsonProperty("is_required")
    private Boolean isRequired;
}
