package com.example.multiform.entities.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "personal_access_token")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalAccessTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;
    
    @Column(nullable = false, name = "tokenable_type")
    private String tokenableType;
    
    @Column(nullable = false, name = "name")
    private String name;
    
    @Column(nullable = false, name = "token")
    private String token;
    
    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci", name = "abilities")
    private String abilities;
    
    @UpdateTimestamp
    @Column(name = "last_used_at")
    private Date lastUsedAt;
    
    @Column(name = "expires_at")
    private Date expiresAt;
    
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
