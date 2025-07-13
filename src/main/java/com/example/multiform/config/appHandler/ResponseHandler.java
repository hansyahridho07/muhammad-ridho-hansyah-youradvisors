package com.example.multiform.config.appHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseHandler<T> {
//    private static final String K = ;
    @NotNull
    private String message;
    
    private T user;      // For login response
    private T form;      // For form response
    private Map<String, List<String>> errors; // For validation errors
    private Object error;
    
    // Generic success response
    public static <T> ResponseEntity<Object> success(String message, String fieldName, T data, HttpStatus status) {
        ResponseHandler<T> response = new ResponseHandler<>();
        response.setMessage(message);
        
        // Use reflection to set the field dynamically
        try {
            Field field = response.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(response, data);
        } catch (Exception e) {
            // Fallback: could add a generic 'data' field
        }
        return new ResponseEntity(response, status);
    }
    
    public static <T> ResponseEntity<Object> success(String message, String fieldName, List<T> data, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put(fieldName, data);
        
        return new ResponseEntity<>(response, status);
    }
    
    // Array errors response
    public static <T> ResponseEntity<T> validationError(String message, Map<String, List<String>> errors, HttpStatus status) {
        ResponseHandler<T> result = ResponseHandler.<T>builder()
            .message(message)
            .errors(errors)
            .build();
        return new ResponseEntity(result, status);
    }
    
    // Object error response
    public static <T> ResponseEntity<T> validationError(String message, Object data, HttpStatus status) {
        ResponseHandler<T> result = ResponseHandler.<T>builder()
            .message(message)
            .error(data)
            .build();
        return new ResponseEntity(result, status);
    }
    
    public static <T> ResponseEntity<T> output(int code, HttpStatus status, String msg, T data) {
        Map<String, Object> meta = new HashMap();
        meta.put("code", code);
        meta.put("status", status);
        meta.put("message", msg);
        Map<String, Object> mapResponse = new HashMap<>();
        mapResponse.put("meta", meta);
        mapResponse.put("data", data);
        return new ResponseEntity(mapResponse, status);
    }
}
