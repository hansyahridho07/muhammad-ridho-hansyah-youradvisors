package com.example.multiform.entities.company;

import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.entities.form.FormEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Company entity yang merepresentasikan sebagai multi tenant
 *
 * @author Muhammad Ridho Hansyah
 * @version 1.0
 */

@Table(
    name = "companies",
    indexes = {
        @Index(name = "idx_code", columnList = "code", unique = true)
    }
)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(unique = true, nullable = false)
    private String code;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FormEntity> forms = new ArrayList<>();
}
