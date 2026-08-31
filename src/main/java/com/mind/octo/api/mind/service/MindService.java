package com.mind.octo.api.mind.service;

import com.mind.octo.api.mind.dto.CreateMindRequest;
import com.mind.octo.api.mind.dto.MindResponse;
import com.mind.octo.api.mind.dto.UpdateMindRequest;
import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.mind.exception.MindNotFoundException;
import com.mind.octo.api.exception.UserNotFoundException;
import com.mind.octo.api.mind.repository.MindRepository;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.repository.OctoUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MindService {

    private final MindRepository mindRepository;
    private final OctoUserRepository octoUserRepository;

    public MindService(
            MindRepository mindRepository,
            OctoUserRepository octoUserRepository
    ) {
        this.mindRepository = mindRepository;
        this.octoUserRepository = octoUserRepository;
    }

    public MindResponse createMind(Long userId, CreateMindRequest request) {

        OctoUserEntity user = octoUserRepository
                .findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        MindEntity mind = new MindEntity();
        mind.setName(request.name());
        mind.setDescription(request.description());
        mind.setIcon(request.icon());
        mind.setColor(request.color());
        mind.setUser(user);

        MindEntity savedMind = mindRepository.save(mind);

        return new MindResponse(
                savedMind.getId(),
                savedMind.getName(),
                savedMind.getDescription(),
                savedMind.getIcon(),
                savedMind.getColor(),
                savedMind.isArchived(),
                savedMind.getCreatedAt(),
                savedMind.getUpdatedAt()
        );
    }

    public List<MindResponse> getUserMinds(Long userId) {

        return mindRepository.findAllByUserId(userId)
                .stream()
                .map(mind -> new MindResponse(
                        mind.getId(),
                        mind.getName(),
                        mind.getDescription(),
                        mind.getIcon(),
                        mind.getColor(),
                        mind.isArchived(),
                        mind.getCreatedAt(),
                        mind.getUpdatedAt()
                ))
                .toList();
    }

    public MindResponse getMindById(Long userId, Long mindId) {

        MindEntity mind = mindRepository
                .findByIdAndUserId(mindId, userId)
                .orElseThrow(() ->
                        new MindNotFoundException("Mind not found")
                );

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

    public MindResponse updateMind(
            Long userId,
            Long mindId,
            UpdateMindRequest request
    ) {

        MindEntity mind = mindRepository
                .findByIdAndUserId(mindId, userId)
                .orElseThrow(() ->
                        new MindNotFoundException("Mind not found")
                );

        mind.setName(request.name());
        mind.setDescription(request.description());
        mind.setIcon(request.icon());
        mind.setColor(request.color());

        MindEntity savedMind = mindRepository.save(mind);

        return new MindResponse(
                savedMind.getId(),
                savedMind.getName(),
                savedMind.getDescription(),
                savedMind.getIcon(),
                savedMind.getColor(),
                savedMind.isArchived(),
                savedMind.getCreatedAt(),
                savedMind.getUpdatedAt()
        );
    }
}