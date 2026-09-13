package com.mind.octo.api.mindcombination.repository;

import com.mind.octo.api.mindcombination.entity.MindCombinationSuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MindCombinationSuggestionRepository
        extends JpaRepository<MindCombinationSuggestionEntity, Long> {

    List<MindCombinationSuggestionEntity>
            findAllByCombinationIdOrderByCreatedAtAsc(Long combinationId);
}