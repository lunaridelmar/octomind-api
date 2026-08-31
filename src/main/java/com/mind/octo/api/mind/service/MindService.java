package com.mind.octo.api.mind.service;

import com.mind.octo.api.mind.dto.CreateMindRequest;
import com.mind.octo.api.mind.dto.MindResponse;
import com.mind.octo.api.mind.dto.UpdateMindRequest;
import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.mind.exception.MindNotFoundException;
import com.mind.octo.api.mind.repository.MindRepository;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.exception.UserNotFoundException;
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

        return toResponse(savedMind);
    }

    public List<MindResponse> getUserMinds(Long userId) {

        return mindRepository.findAllByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MindResponse getMindById(Long userId, Long mindId) {

        MindEntity mind = mindRepository
                .findByIdAndUserId(mindId, userId)
                .orElseThrow(() ->
                        new MindNotFoundException("Mind not found")
                );

        return toResponse(mind);
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

        return toResponse(savedMind);
    }

    public MindResponse archiveMind(Long userId, Long mindId) {

        MindEntity mind = mindRepository
                .findByIdAndUserId(mindId, userId)
                .orElseThrow(() ->
                        new MindNotFoundException("Mind not found")
                );

        mind.setArchived(true);

        MindEntity savedMind = mindRepository.save(mind);

        return toResponse(savedMind);
    }

    public MindResponse restoreMind(Long userId, Long mindId) {

        MindEntity mind = mindRepository
                .findByIdAndUserId(mindId, userId)
                .orElseThrow(() ->
                        new MindNotFoundException("Mind not found")
                );

        mind.setArchived(false);

        MindEntity savedMind = mindRepository.save(mind);

        return toResponse(savedMind);
    }

    public List<MindResponse> getActiveMinds(Long userId) {

        return mindRepository.findAllByUserIdAndArchivedFalse(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<MindResponse> getArchivedMinds(Long userId) {

        return mindRepository.findAllByUserIdAndArchivedTrue(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private MindResponse toResponse(MindEntity mind) {
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