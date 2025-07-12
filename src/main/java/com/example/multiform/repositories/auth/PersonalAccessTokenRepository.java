package com.example.multiform.repositories.auth;

import com.example.multiform.entities.auth.PersonalAccessTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalAccessTokenRepository extends CrudRepository<PersonalAccessTokenEntity, Long> {
}
