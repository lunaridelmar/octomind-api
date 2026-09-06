package com.mind.octo.api.mindcombination.prompt;

import com.mind.octo.api.mind.entity.MindEntity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class MindCombinationPromptBuilder {

    private static final String PROMPT_PATH =
            "prompts/mind-combination.txt";

    private final String promptTemplate;

    public MindCombinationPromptBuilder() {
        this.promptTemplate = loadPromptTemplate();
    }

    public String build(List<MindEntity> minds) {
        StringBuilder prompt = new StringBuilder(promptTemplate);

        prompt.append("\n\nMinds:\n");

        for (int i = 0; i < minds.size(); i++) {
            MindEntity mind = minds.get(i);

            prompt.append("\n")
                    .append(i + 1)
                    .append(". ")
                    .append(mind.getName());

            if (mind.getDescription() != null
                    && !mind.getDescription().isBlank()) {

                prompt.append("\n   Description: ")
                        .append(mind.getDescription());
            }

            prompt.append("\n");
        }

        return prompt.toString();
    }

    private String loadPromptTemplate() {
        ClassPathResource resource =
                new ClassPathResource(PROMPT_PATH);

        try {
            return resource.getContentAsString(
                    StandardCharsets.UTF_8
            ).trim();
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not load Mind combination prompt",
                    exception
            );
        }
    }
}