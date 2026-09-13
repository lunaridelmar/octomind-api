package com.mind.octo.api.activity.service;

import com.mind.octo.api.activity.dto.ActivityResponse;
import com.mind.octo.api.activity.dto.CreateActivityRequest;
import com.mind.octo.api.activity.entity.ActivityEntity;
import com.mind.octo.api.activity.exception.InvalidActivityException;
import com.mind.octo.api.activity.repository.ActivityRepository;
import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.mind.repository.MindRepository;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.exception.UserNotFoundException;
import com.mind.octo.api.user.repository.OctoUserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final MindRepository mindRepository;
    private final OctoUserRepository octoUserRepository;

    public ActivityService(
            ActivityRepository activityRepository,
            MindRepository mindRepository,
            OctoUserRepository octoUserRepository
    ) {
        this.activityRepository = activityRepository;
        this.mindRepository = mindRepository;
        this.octoUserRepository = octoUserRepository;
    }

    public ActivityResponse createActivity(
            Long userId,
            CreateActivityRequest request
    ) {
        OctoUserEntity user = octoUserRepository
                .findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        List<MindEntity> minds =
                mindRepository.findAllById(request.mindIds());

        boolean allMindsBelongToUser =
                minds.size() == request.mindIds().size()
                        && minds.stream()
                        .allMatch(mind ->
                                mind.getUser().getId().equals(userId)
                        );

        if (!allMindsBelongToUser) {
            throw new InvalidActivityException(
                    "One or more minds are invalid"
            );
        }

        ActivityEntity activity = new ActivityEntity();

        activity.setTitle(request.title());
        activity.setUser(user);
        activity.setMinds(new HashSet<>(minds));

        ActivityEntity saved =
                activityRepository.save(activity);

        Set<Long> mindIds = saved.getMinds()
                .stream()
                .map(MindEntity::getId)
                .collect(java.util.stream.Collectors.toSet());

        return new ActivityResponse(
                saved.getId(),
                saved.getTitle(),
                saved.isCompleted(),
                mindIds,
                saved.getCreatedAt(),
                saved.getCompletedAt()
        );
    }

    public List<ActivityResponse> getUserActivities(Long userId) {
        return activityRepository
                .findAllByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ActivityResponse getActivityById(
            Long userId,
            Long activityId
    ) {
        ActivityEntity activity = activityRepository
                .findByIdAndUserId(activityId, userId)
                .orElseThrow(() ->
                        new InvalidActivityException(
                                "Activity not found"
                        )
                );

        return toResponse(activity);
    }

    public ActivityResponse completeActivity(
            Long userId,
            Long activityId
    ) {
        ActivityEntity activity = activityRepository
                .findByIdAndUserId(activityId, userId)
                .orElseThrow(() ->
                        new InvalidActivityException(
                                "Activity not found"
                        )
                );

        if (activity.isCompleted()) {
            return toResponse(activity);
        }

        activity.setCompleted(true);
        activity.setCompletedAt(Instant.now());

        ActivityEntity saved =
                activityRepository.save(activity);

        return toResponse(saved);
    }

    private ActivityResponse toResponse(ActivityEntity activity) {
        Set<Long> mindIds = activity.getMinds()
                .stream()
                .map(MindEntity::getId)
                .collect(java.util.stream.Collectors.toSet());

        return new ActivityResponse(
                activity.getId(),
                activity.getTitle(),
                activity.isCompleted(),
                mindIds,
                activity.getCreatedAt(),
                activity.getCompletedAt()
        );
    }
}