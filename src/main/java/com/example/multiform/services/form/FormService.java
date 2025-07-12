package com.example.multiform.services.form;

import com.example.multiform.data.form.request.FormDataRequest;
import com.example.multiform.data.form.response.FormDataResponse;
import com.example.multiform.data.form.response.FormDetailResponse;
import com.example.multiform.data.general.request.PaginationParam;
import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.entities.form.AllowedDomainEntity;
import com.example.multiform.entities.form.FormEntity;
import com.example.multiform.helpers.GetDataUser;
import com.example.multiform.interfaces.IFormHandler;
import com.example.multiform.repositories.form.AllowedDomainRepository;
import com.example.multiform.mappers.form.IFormMapper;
import com.example.multiform.repositories.form.FormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FormService implements IFormHandler{
    private final IFormMapper formMapper;
    private final FormRepository formRepository;
    private final AllowedDomainRepository allowedDomainRepository;
    
    @Override
    public FormDataResponse createForm(FormDataRequest formDataRequest) {
        UserEntity currentUser = GetDataUser.getUser();
        formDataRequest.setEmail(currentUser.getEmail());
        FormEntity form = formRepository.save(formMapper.toFormEntity(formDataRequest));
        
        List<AllowedDomainEntity> allowedDomains = formDataRequest.getAllowedDomains().stream()
            .map(domain -> createAllowedDomainEntity(domain, form)).toList();
        allowedDomainRepository.saveAll(allowedDomains);
        return formMapper.toFormDataResponse(form);
    }
    
    @Override
    public List<FormDataResponse> findAllForms(PaginationParam param) {
        Pageable pageable = PageRequest.of(param.getPage() - 1, param.getLimit());
        //TODO: filter berdasarkan prefix email user
        return formRepository.findAllBy(pageable).stream()
            .map(formMapper::toFormDataResponse).toList();
    }
    
    @Override
    @Transactional
    public FormDetailResponse getFormDetail(String formId) {
        FormEntity form = formRepository.findById(Long.parseLong(formId))
            .orElseThrow(() -> new NoSuchElementException("Form not found"));
        form.getQuestions().size();
        form.getAllowedDomains().size();
//        form.getCreator();
        return formMapper.toFormDetailResponse(form);
    }
    
    private AllowedDomainEntity createAllowedDomainEntity(String domain, FormEntity form) {
        return AllowedDomainEntity.builder()
            .domain(domain)
            .user(form.getCreator())
            .form(form)
            .build();
    }
}
