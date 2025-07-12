package com.example.multiform.repositories.form;

import com.example.multiform.entities.form.FormEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends CrudRepository<FormEntity, Long> {

    List<FormEntity> findAllBy(Pageable pageable);
}
