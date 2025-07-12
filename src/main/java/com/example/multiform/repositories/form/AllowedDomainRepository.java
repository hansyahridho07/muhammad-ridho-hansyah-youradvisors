package com.example.multiform.repositories.form;

import com.example.multiform.entities.form.AllowedDomainEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowedDomainRepository extends CrudRepository<AllowedDomainEntity, Long> {
}
