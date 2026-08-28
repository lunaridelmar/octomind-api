package com.mind.octo.api.user.service;

import com.mind.octo.api.user.dto.OctoUserResponse;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.dto.OctoUserRegistrationRequest;
import com.mind.octo.api.user.exception.EmailAlreadyRegisteredException;
import com.mind.octo.api.user.repository.OctoUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OctoUserService {

    private final OctoUserRepository octoUserRepository;
    private final PasswordEncoder passwordEncoder;

    public OctoUserService(
            OctoUserRepository octoUserRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.octoUserRepository = octoUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public OctoUserResponse register(OctoUserRegistrationRequest request) {

        if (octoUserRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyRegisteredException("Email is already registered");
        }

        OctoUserEntity octoUserEntity = new OctoUserEntity();
        octoUserEntity.setEmail(request.email());
        octoUserEntity.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        octoUserEntity.setDisplayName(request.displayName());

        OctoUserEntity savedUser = octoUserRepository.save(octoUserEntity);

        return new OctoUserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getDisplayName(),
                savedUser.getCreatedAt()
        );
    }
}