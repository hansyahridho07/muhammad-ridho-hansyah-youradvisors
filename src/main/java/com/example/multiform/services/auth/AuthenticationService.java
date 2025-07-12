package com.example.multiform.services.auth;

import com.example.multiform.data.auth.request.LoginDataRequest;
import com.example.multiform.data.auth.request.RegisterDataRequest;
import com.example.multiform.data.auth.response.LoginDataResponse;
import com.example.multiform.data.auth.response.RegisterDataResponse;
import com.example.multiform.entities.auth.PersonalAccessTokenEntity;
import com.example.multiform.entities.auth.UserEntity;
import com.example.multiform.enums.StatusData;
import com.example.multiform.interfaces.IAuthenticationHandler;
import com.example.multiform.mappers.auth.IAuthMapper;
import com.example.multiform.repositories.auth.PersonalAccessTokenRepository;
import com.example.multiform.repositories.auth.UserRepository;
import com.example.multiform.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationHandler {
    private final UserRepository userRepository;
    private final PersonalAccessTokenRepository personalAccessTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IAuthMapper authMapper;
    private final HttpServletRequest request;
    private final JwtService jwtService;
    
    @Value("${domain.url}")
    private String DOMAIN_URL;
    
    @Override
    public RegisterDataResponse signup(RegisterDataRequest input) {
        UserEntity userEntity = authMapper.toUser(input, passwordEncoder, UUID.randomUUID());
        userRepository.save(userEntity);
        String urlVerification = DOMAIN_URL + "/api/v1/auth/verify/" + userEntity.getRememberToken();
        return RegisterDataResponse.builder()
            .name(userEntity.getName())
            .email(userEntity.getEmail())
            .urlVerification(urlVerification)
            .build();
    }
    
    @Override
    public void verify(String rememberToken){
        UserEntity userEntity = userRepository.findByRememberToken(rememberToken)
            .orElseThrow(() -> new NoSuchElementException("user not found"));
        
        // jika sudah sukses verifed email langsung return
        if(userEntity.getEmailVerifiedAt() != null){
            return;
        }
        
        userEntity.setEmailVerifiedAt(new Date());
        userRepository.save(userEntity);
    }
    
    @Override
    public LoginDataResponse authenticate(LoginDataRequest input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );
        UUID companyId = UUID.fromString(Optional.ofNullable(request.getHeader("company_id"))
            .orElseThrow(() -> new NoSuchElementException("Company ID Not Set")));
        UserEntity userEntity = userRepository.findByEmailAndCompanyIdAndStatus(input.getEmail(), companyId, StatusData.ACTIVE)
            .orElseThrow(() -> new NoSuchElementException("email or password incorrect"));
        if(userEntity.getEmailVerifiedAt() == null){
            throw new NoSuchElementException("email not verified");
        }
        String jwtToken = jwtService.generateToken(userEntity);
        setPersonalAccessToken(jwtToken, userEntity);
        return authMapper.toLoginDataResponse(jwtToken, jwtService);
    }
    
    private void setPersonalAccessToken(String token, UserEntity user){
        LocalDateTime datePlusOneHour = LocalDateTime.now().plusHours(1);
        Date convertToDate = Date.from(datePlusOneHour.atZone(ZoneId.systemDefault()).toInstant());
        PersonalAccessTokenEntity personalAccessToken = new PersonalAccessTokenEntity().builder()
            .tokenableType("Bearer")
            .token(UUID.randomUUID().toString())
            .name(user.getEmail())
            .abilities(token)
            .expiresAt(convertToDate)
            .build();
        personalAccessTokenRepository.save(personalAccessToken);
    }
    
}
