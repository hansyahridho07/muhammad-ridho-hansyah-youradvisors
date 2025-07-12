package com.example.multiform.data.form.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FormDataRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;
    
    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-zA-Z0-9.-]+$", message = "Slug must contain only alphanumeric characters, dash (-), and dot (.)")
    @Pattern(regexp = "^\\S+$", message = "Slug cannot contain spaces")
    @Size(max = 100, message = "Slug cannot exceed 100 characters")
    private String slug;
    
    @JsonProperty("allowed_domains")
    @NotNull(message = "Allowed domains is required")
    @Size(min = 1, message = "At least one domain is required")
    private List<@NotBlank(message = "Domain cannot be empty") String> allowedDomains;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
    
    @JsonProperty("limit_one_response")
    private Boolean limitOneResponse = false;
    
    private String email;
}
