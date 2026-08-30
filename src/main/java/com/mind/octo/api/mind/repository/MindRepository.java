package com.mind.octo.api.mind.repository;

import com.mind.octo.api.mind.entity.MindEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MindRepository extends JpaRepository<MindEntity, Long> {

    List<MindEntity> findAllByUserId(Long userId);
    Optional<MindEntity> findByIdAndUserId(Long id, Long userId);
}