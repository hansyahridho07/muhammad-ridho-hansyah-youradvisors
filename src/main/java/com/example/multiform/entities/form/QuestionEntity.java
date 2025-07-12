package com.example.multiform.entities.form;

import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.entities.company.CompanyEntity;
import com.example.multiform.enums.QuestionChoiceEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "questions")
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "choice_type", nullable = false)
    private QuestionChoiceEnum choiceType;
    
    @Column(name = "choices")
    private String choices; // JSON string or comma-separated values
    
    @Column(name = "is_required")
    private Boolean isRequired;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnswerEntity> questions = new ArrayList<>();
}
