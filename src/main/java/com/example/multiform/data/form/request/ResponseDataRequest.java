package com.example.multiform.data.form.request;

import com.example.multiform.embed.ResponseDataEmbed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataRequest {
    private List<ResponseDataEmbed> answers;
}
