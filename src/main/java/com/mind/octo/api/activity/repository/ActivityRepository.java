package com.mind.octo.api.activity.repository;

import com.mind.octo.api.activity.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    List<ActivityEntity> findAllByUserId(Long userId);

    Optional<ActivityEntity> findByIdAndUserId(Long id, Long userId);
}