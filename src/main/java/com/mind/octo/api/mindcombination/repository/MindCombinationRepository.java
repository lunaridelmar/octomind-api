package com.mind.octo.api.mindcombination.repository;

import com.mind.octo.api.mindcombination.entity.MindCombinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MindCombinationRepository
        extends JpaRepository<MindCombinationEntity, Long> {
    List<MindCombinationEntity> findAllByUserId(Long userId);
    Optional<MindCombinationEntity> findByIdAndUserId(Long id, Long userId);
}