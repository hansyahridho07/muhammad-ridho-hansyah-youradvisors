package com.example.multiform.repositories.auth;

import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.enums.StatusData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndCompanyIdAndStatus(String email, UUID companyId, StatusData status);
    
    Optional<UserEntity> findByRememberToken(String rememberToken);
}
