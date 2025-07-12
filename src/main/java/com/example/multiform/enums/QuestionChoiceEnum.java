package com.example.multiform.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum QuestionChoiceEnum {
    SHORT_ANSWER("short answer"),
    PARAGRAPH("paragraph"),
    DATE("date"),
    TIME("time"),
    MULTIPLE_CHOICE("multiple choice"),
    DROPDOWN("dropdown"),
    CHECKBOXES("checkboxes");
    
    private final String name;
    
    // Method untuk mencari enum dari string
    public static Optional<QuestionChoiceEnum> fromString(String text) {
        return Arrays.stream(values())
            .filter(choice -> choice.getName().equalsIgnoreCase(text))
            .findFirst();
    }
    
    // Method untuk mendapatkan enum atau throw exception
    public static QuestionChoiceEnum fromStringOrThrow(String text) {
        return fromString(text)
            .orElseThrow(() -> new IllegalArgumentException("Invalid choice: " + text));
    }
}
