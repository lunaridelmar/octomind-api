package com.mind.octo.api.mindcombination.service;

import com.mind.octo.api.mind.dto.MindResponse;
import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.mind.repository.MindRepository;
import com.mind.octo.api.mindcombination.dto.CreateMindCombinationRequest;
import com.mind.octo.api.mindcombination.dto.MindCombinationResponse;
import com.mind.octo.api.mindcombination.entity.MindCombinationEntity;
import com.mind.octo.api.mindcombination.exception.InvalidMindCombinationException;
import com.mind.octo.api.mindcombination.repository.MindCombinationRepository;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.exception.UserNotFoundException;
import com.mind.octo.api.user.repository.OctoUserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MindCombinationService {

    private final MindCombinationRepository mindCombinationRepository;
    private final MindRepository mindRepository;
    private final OctoUserRepository octoUserRepository;

    public MindCombinationService(
            MindCombinationRepository mindCombinationRepository,
            MindRepository mindRepository,
            OctoUserRepository octoUserRepository
    ) {
        this.mindCombinationRepository = mindCombinationRepository;
        this.mindRepository = mindRepository;
        this.octoUserRepository = octoUserRepository;
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