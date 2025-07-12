package com.example.multiform.repositories.auth;

import com.example.multiform.entities.company.CompanyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findByCodeOrEmail(String code, String email);
    Optional<CompanyEntity> findByCode(String code);
}
