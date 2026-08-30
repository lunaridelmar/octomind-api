package com.mind.octo.api.mind.coltroller;

import com.mind.octo.api.mind.dto.CreateMindRequest;
import com.mind.octo.api.mind.dto.MindResponse;
import com.mind.octo.api.mind.service.MindService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/minds")
public class MindController {

    private final MindService mindService;

    public MindController(MindService mindService) {
        this.mindService = mindService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MindResponse createMind(
            Authentication authentication,
            @Valid @RequestBody CreateMindRequest request
    ) {
        Long userId = (Long) authentication.getPrincipal();

        return mindService.createMind(userId, request);
    }

    @GetMapping
    public List<MindResponse> getMinds(Authentication authentication) {

        Long userId = (Long) authentication.getPrincipal();

        return mindService.getUserMinds(userId);
    }

    @GetMapping("/{id}")
    public MindResponse getMind(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long userId = (Long) authentication.getPrincipal();

        return mindService.getMindById(userId, id);
    }
}