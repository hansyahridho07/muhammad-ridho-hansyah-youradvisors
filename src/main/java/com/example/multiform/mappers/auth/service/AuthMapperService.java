package com.example.multiform.mappers.auth.service;

import com.example.multiform.data.auth.request.RegisterDataRequest;
import com.example.multiform.embed.QuestionDataEmbed;
import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.entities.company.CompanyEntity;
import com.example.multiform.entities.form.AllowedDomainEntity;
import com.example.multiform.entities.form.QuestionEntity;
import com.example.multiform.enums.StatusData;
import com.example.multiform.repositories.auth.CompanyRepository;
import com.example.multiform.repositories.auth.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Name;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class AuthMapperService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final HttpServletRequest request;
    
    @Named("setCompany")
    public CompanyEntity setCompany(Object _req) {
        UUID company_id = UUID.fromString(Optional.ofNullable(request.getHeader("company_id"))
            .orElseThrow(() -> new NoSuchElementException("Company ID Not Set")));
        return companyRepository.findById(company_id)
            .orElseThrow(() -> new NoSuchElementException("Company not found"));
    }
    
    @Named("setUser")
    public UserEntity setUser(String email) {
        UUID company_id = UUID.fromString(Optional.ofNullable(request.getHeader("company_id"))
            .orElseThrow(() -> new NoSuchElementException("Company Code Not Set")));
        return userRepository.findByEmailAndCompanyIdAndStatus(email, company_id, StatusData.ACTIVE)
            .orElseThrow(() -> new NoSuchElementException("User not found"));
    }
    
    @Named("setIntegerToBoolean")
    public Boolean setLimitOneResponse(Integer limit) {
        return limit != 0;
    }
    
    @Named("setBooleanToInteger")
    public Integer setLimitOneResponseToInteger(Boolean limit) {
        return limit ? 1 : 0;
    }
    
    @Named("setArrayAllowedDomainsToArrayOfString")
    public List<String> setArrayToString(List<AllowedDomainEntity> array) {
        if(array == null || array.isEmpty()) return null;
        List<String> domains = new ArrayList<>();
        array.forEach(el -> domains.add(el.getDomain()));
        return domains;
//        return String.join(",", domains);
    }
}
