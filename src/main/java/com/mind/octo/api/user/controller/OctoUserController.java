package com.mind.octo.api.user.controller;

import com.mind.octo.api.user.dto.OctoUserLoginRequest;
import com.mind.octo.api.user.dto.OctoUserRegistrationRequest;
import com.mind.octo.api.user.dto.OctoUserResponse;
import com.mind.octo.api.user.service.OctoUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class OctoUserController {

    private final OctoUserService octoUserService;

    public OctoUserController(OctoUserService octoUserService) {
        this.octoUserService = octoUserService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public OctoUserResponse register(
            @Valid @RequestBody OctoUserRegistrationRequest request
    ) {
        return octoUserService.register(request);
    }

    @PostMapping("/login")
    public OctoUserResponse login(
            @Valid @RequestBody OctoUserLoginRequest request
    ) {
        return octoUserService.login(request);
    }
}