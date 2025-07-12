package com.example.multiform.data.form.response;

import com.example.multiform.embed.QuestionDataEmbed;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FormDetailResponse extends FormDataResponse{
    @JsonProperty("allowed_domains")
    private List<String> allowedDomains;
    
    private List<QuestionDataEmbed> questions;
}
