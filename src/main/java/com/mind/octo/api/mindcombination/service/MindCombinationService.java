package com.mind.octo.api.mindcombination.service;

import com.mind.octo.api.mind.dto.MindResponse;
import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.mind.repository.MindRepository;
import com.mind.octo.api.mindcombination.dto.*;
import com.mind.octo.api.mindcombination.entity.MindCombinationEntity;
import com.mind.octo.api.mindcombination.entity.MindCombinationSuggestionEntity;
import com.mind.octo.api.mindcombination.exception.InvalidMindCombinationException;
import com.mind.octo.api.mindcombination.exception.NotEnoughMindsException;
import com.mind.octo.api.mindcombination.generator.MindCombinationGenerator;
import com.mind.octo.api.mindcombination.repository.MindCombinationRepository;
import com.mind.octo.api.mindcombination.repository.MindCombinationSuggestionRepository;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.exception.UserNotFoundException;
import com.mind.octo.api.user.repository.OctoUserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MindCombinationService {

    private final MindCombinationRepository mindCombinationRepository;
    private final MindRepository mindRepository;
    private final OctoUserRepository octoUserRepository;
    private final MindCombinationGenerator mindCombinationGenerator;
    private final MindCombinationSuggestionRepository suggestionRepository;

    public MindCombinationService(
            MindCombinationRepository mindCombinationRepository,
            MindRepository mindRepository,
            OctoUserRepository octoUserRepository, MindCombinationGenerator mindCombinationGenerator, MindCombinationSuggestionRepository suggestionRepository
    ) {
        this.mindCombinationRepository = mindCombinationRepository;
        this.mindRepository = mindRepository;
        this.octoUserRepository = octoUserRepository;
        this.mindCombinationGenerator = mindCombinationGenerator;
        this.suggestionRepository = suggestionRepository;
    }

    public MindCombinationResponse createCombination(
            Long userId,
            CreateMindCombinationRequest request
    ) {
        OctoUserEntity user = octoUserRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<MindEntity> minds = mindRepository.findAllById(request.mindIds());

        Set<Long> foundMindIds = minds.stream()
                .map(MindEntity::getId)
                .collect(java.util.stream.Collectors.toSet());

        if (!foundMindIds.equals(request.mindIds())) {
            throw new InvalidMindCombinationException(
                    "One or more minds are invalid"
            );
        }

        boolean containsForeignMind = minds.stream()
                .anyMatch(mind -> !mind.getUser().getId().equals(userId));

        if (containsForeignMind) {
            throw new InvalidMindCombinationException(
                    "One or more minds are invalid"
            );
        }

        MindCombinationEntity combination = new MindCombinationEntity();
        combination.setUser(user);
        combination.setMinds(new HashSet<>(minds));

        MindCombinationEntity savedCombination =
                mindCombinationRepository.save(combination);

        return toResponse(savedCombination);
    }

    public List<MindCombinationResponse> getUserCombinations(Long userId) {
        return mindCombinationRepository.findAllByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MindCombinationResponse getCombinationById(
            Long userId,
            Long combinationId
    ) {
        MindCombinationEntity combination = mindCombinationRepository
                .findByIdAndUserId(combinationId, userId)
                .orElseThrow(() ->
                        new InvalidMindCombinationException("Mind combination not found")
                );

        return toResponse(combination);
    }

    public MindCombinationResponse createRandomCombination(
            Long userId,
            RandomMindCombinationRequest request
    ) {
        OctoUserEntity user = octoUserRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<MindEntity> activeMinds =
                mindRepository.findAllByUserIdAndArchivedFalse(userId);

        if (activeMinds.size() < request.count()) {
            throw new NotEnoughMindsException(
                    "Not enough active minds to create this combination"
            );
        }

        List<MindEntity> shuffledMinds = new ArrayList<>(activeMinds);
        Collections.shuffle(shuffledMinds);

        Set<MindEntity> selectedMinds = new HashSet<>(
                shuffledMinds.subList(0, request.count())
        );

        MindCombinationEntity combination = new MindCombinationEntity();
        combination.setUser(user);
        combination.setMinds(selectedMinds);

        MindCombinationEntity savedCombination =
                mindCombinationRepository.save(combination);

        return toResponse(savedCombination);
    }

    public MindCombinationSuggestionsResponse generateSuggestions(
            Long userId,
            Long combinationId
    ) {
        MindCombinationEntity combination = mindCombinationRepository
                .findByIdAndUserId(combinationId, userId)
                .orElseThrow(() ->
                        new InvalidMindCombinationException(
                                "Mind combination not found"
                        )
                );

        List<MindEntity> minds = new ArrayList<>(combination.getMinds());

        List<String> suggestions =
                mindCombinationGenerator.generate(minds);

        List<MindCombinationSuggestionEntity> entities =
                suggestions.stream()
                        .map(text -> {
                            MindCombinationSuggestionEntity entity =
                                    new MindCombinationSuggestionEntity();

                            entity.setText(text);
                            entity.setCombination(combination);

                            return entity;
                        })
                        .toList();

        suggestionRepository.saveAll(entities);

        return new MindCombinationSuggestionsResponse(
                combination.getId(),
                suggestions
        );
    }

    public List<MindCombinationSuggestionResponse> getSuggestions(
            Long userId,
            Long combinationId
    ) {
        MindCombinationEntity combination = mindCombinationRepository
                .findByIdAndUserId(combinationId, userId)
                .orElseThrow(() ->
                        new InvalidMindCombinationException(
                                "Mind combination not found"
                        )
                );

        return suggestionRepository
                .findAllByCombinationIdOrderByCreatedAtAsc(combination.getId())
                .stream()
                .map(suggestion ->
                        new MindCombinationSuggestionResponse(
                                suggestion.getId(),
                                suggestion.getText(),
                                suggestion.getCreatedAt()
                        )
                )
                .toList();
    }

    private MindCombinationResponse toResponse(
            MindCombinationEntity combination
    ) {
        Set<MindResponse> minds = combination.getMinds()
                .stream()
                .map(this::toMindResponse)
                .collect(java.util.stream.Collectors.toSet());

        return new MindCombinationResponse(
                combination.getId(),
                minds,
                combination.getCreatedAt()
        );
    }

    private MindResponse toMindResponse(MindEntity mind) {
        return new MindResponse(
                mind.getId(),
                mind.getName(),
                mind.getDescription(),
                mind.getIcon(),
                mind.getColor(),
                mind.isArchived(),
                mind.getCreatedAt(),
                mind.getUpdatedAt()
        );
    }
}