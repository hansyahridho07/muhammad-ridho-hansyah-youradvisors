package com.example.multiform.repositories.form;

import com.example.multiform.entities.form.QuestionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
    Optional<QuestionEntity> findByIdAndFormId(Long id, Integer form_id);
}
