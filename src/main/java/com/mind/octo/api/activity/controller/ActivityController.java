package com.mind.octo.api.activity.controller;

import com.mind.octo.api.activity.dto.ActivityResponse;
import com.mind.octo.api.activity.dto.CreateActivityRequest;
import com.mind.octo.api.activity.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> createActivity(
            Authentication authentication,
            @Valid @RequestBody CreateActivityRequest request
    ) {
        Long userId = (Long) authentication.getPrincipal();

        ActivityResponse response =
                activityService.createActivity(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(
            Authentication authentication
    ) {
        Long userId = (Long) authentication.getPrincipal();

        List<ActivityResponse> activities =
                activityService.getUserActivities(userId);

        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(
            Authentication authentication,
            @PathVariable Long id
    ) {
        Long userId = (Long) authentication.getPrincipal();

        ActivityResponse response =
                activityService.getActivityById(userId, id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ActivityResponse> completeActivity(
            Authentication authentication,
            @PathVariable Long id
    ) {
        Long userId = (Long) authentication.getPrincipal();

        ActivityResponse response =
                activityService.completeActivity(userId, id);

        return ResponseEntity.ok(response);
    }
}