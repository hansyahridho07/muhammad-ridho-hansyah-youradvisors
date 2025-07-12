package com.example.multiform.data.form.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FormDataResponse {
    private String name;
    private String slug;
    private String description;
    @JsonProperty("limit_one_response")
    private Boolean limitOneResponse;
    @JsonProperty("creator_id")
    private Integer creatorId;
    private Integer id;
}
