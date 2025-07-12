package com.example.multiform.data.form.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataResponse {
    private String date;
    private UserResponse user;
    private AnswerResponse answers;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        private Integer id;
        private String name;
        private String email;
        @JsonProperty("email_verified_at")
        private Date emailVerifiedAt;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerResponse {
        @JsonProperty("Name")
        private String name;
        
        @JsonProperty("Address")
        private String address;
        
        @JsonProperty("Born Date")
        private String bornDate;
        
        @JsonProperty("Sex")
        private String sex;
        
        @JsonProperty("Value")
        private String value;
    }
}
