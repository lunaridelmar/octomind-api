package com.mind.octo.api.user.service;

import com.mind.octo.api.security.JwtService;
import com.mind.octo.api.user.dto.OctoUserLoginRequest;
import com.mind.octo.api.user.dto.OctoUserLoginResponse;
import com.mind.octo.api.user.dto.OctoUserResponse;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.dto.OctoUserRegistrationRequest;
import com.mind.octo.api.user.exception.EmailAlreadyRegisteredException;
import com.mind.octo.api.user.exception.InvalidCredentialsException;
import com.mind.octo.api.user.repository.OctoUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OctoUserService {

    private final OctoUserRepository octoUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public OctoUserService(
            OctoUserRepository octoUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.octoUserRepository = octoUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public OctoUserResponse register(OctoUserRegistrationRequest request) {

        if (octoUserRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyRegisteredException("Email is already registered");
        }

        OctoUserEntity octoUser = new OctoUserEntity();
        octoUser.setEmail(request.email());
        octoUser.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        octoUser.setDisplayName(request.displayName());

        OctoUserEntity savedUser = octoUserRepository.save(octoUser);

        return new OctoUserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getDisplayName(),
                savedUser.getCreatedAt()
        );
    }

    public OctoUserLoginResponse login(OctoUserLoginRequest request) {

        OctoUserEntity octoUser = octoUserRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.password(),
                octoUser.getPasswordHash()
        )) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(octoUser.getId());

        OctoUserResponse userResponse = new OctoUserResponse(
                octoUser.getId(),
                octoUser.getEmail(),
                octoUser.getDisplayName(),
                octoUser.getCreatedAt()
        );

        return new OctoUserLoginResponse(
                token,
                userResponse
        );
    }

    public OctoUserResponse getUserById(Long userId) {

        OctoUserEntity octoUser = octoUserRepository
                .findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        return new OctoUserResponse(
                octoUser.getId(),
                octoUser.getEmail(),
                octoUser.getDisplayName(),
                octoUser.getCreatedAt()
        );
    }
}