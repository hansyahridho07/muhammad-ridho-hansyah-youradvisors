package com.example.multiform.services.company;

import com.example.multiform.data.company.request.CompanyDataRequest;
import com.example.multiform.entities.company.CompanyEntity;
import com.example.multiform.interfaces.ICompanyHandler;
import com.example.multiform.repositories.auth.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyHandler {
    private final CompanyRepository companyRepository;
    @Override
    public void createCompany(CompanyDataRequest request) {
        companyRepository.findByCodeOrEmail(request.getCode(), request.getEmail()).ifPresent(
            company -> {
                throw new NoSuchElementException("company with code or email already exits");
            }
        );
        CompanyEntity company = new CompanyEntity().builder()
            .name(request.getName())
            .code(request.getCode())
            .email(request.getEmail())
            .build();
        companyRepository.save(company);
    }
}
