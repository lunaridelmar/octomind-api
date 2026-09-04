package com.mind.octo.api.mindcombination.controller;

import com.mind.octo.api.mindcombination.dto.CreateMindCombinationRequest;
import com.mind.octo.api.mindcombination.dto.MindCombinationResponse;
import com.mind.octo.api.mindcombination.dto.MindCombinationSuggestionsResponse;
import com.mind.octo.api.mindcombination.dto.RandomMindCombinationRequest;
import com.mind.octo.api.mindcombination.service.MindCombinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mind-combinations")
public class MindCombinationController {

    private final MindCombinationService mindCombinationService;

    public MindCombinationController(
            MindCombinationService mindCombinationService
    ) {
        this.mindCombinationService = mindCombinationService;
    }

    @PostMapping
    public ResponseEntity<MindCombinationResponse> createCombination(
            Authentication authentication,
            @Valid @RequestBody CreateMindCombinationRequest request
    ) {
        Long userId = (Long) authentication.getPrincipal();

        MindCombinationResponse response =
                mindCombinationService.createCombination(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MindCombinationResponse>> getUserCombinations(
            Authentication authentication
    ) {
        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok(
                mindCombinationService.getUserCombinations(userId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MindCombinationResponse> getCombinationById(
            Authentication authentication,
            @PathVariable Long id
    ) {
        Long userId = (Long) authentication.getPrincipal();

        return ResponseEntity.ok(
                mindCombinationService.getCombinationById(userId, id)
        );
    }

    @PostMapping("/random")
    public ResponseEntity<MindCombinationResponse> createRandomCombination(
            Authentication authentication,
            @Valid @RequestBody RandomMindCombinationRequest request
    ) {
        Long userId = (Long) authentication.getPrincipal();

        MindCombinationResponse response =
                mindCombinationService.createRandomCombination(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/{id}/suggestions")
    public ResponseEntity<MindCombinationSuggestionsResponse> generateSuggestions(
            Authentication authentication,
            @PathVariable Long id
    ) {
        Long userId = (Long) authentication.getPrincipal();

        MindCombinationSuggestionsResponse response =
                mindCombinationService.generateSuggestions(userId, id);

        return ResponseEntity.ok(response);
    }
}