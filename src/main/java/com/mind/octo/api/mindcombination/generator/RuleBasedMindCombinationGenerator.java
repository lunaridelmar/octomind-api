package com.mind.octo.api.mindcombination.generator;

import com.mind.octo.api.mind.entity.MindEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(
        name = "octomind.generator.type",
        havingValue = "rule-based",
        matchIfMissing = true
)
public class RuleBasedMindCombinationGenerator
        implements MindCombinationGenerator {

    @Override
    public List<String> generate(List<MindEntity> minds) {

        String mindNames = minds.stream()
                .map(MindEntity::getName)
                .collect(Collectors.joining(", "));

        return List.of(
                "Create a project that uses all of these areas: " + mindNames,
                "Design a challenge that requires you to use " + mindNames,
                "Create something you can share that connects " + mindNames
        );
    }
}