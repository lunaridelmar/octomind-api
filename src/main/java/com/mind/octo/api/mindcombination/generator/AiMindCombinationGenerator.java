package com.mind.octo.api.mindcombination.generator;

import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.mindcombination.exception.AiServiceUnavailableException;
import com.mind.octo.api.mindcombination.prompt.MindCombinationPromptBuilder;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(
        name = "octomind.generator.type",
        havingValue = "ai"
)
public class AiMindCombinationGenerator
        implements MindCombinationGenerator {

    private final ChatClient chatClient;
    private final MindCombinationPromptBuilder promptBuilder;

    public AiMindCombinationGenerator(
            ChatClient chatClient,
            MindCombinationPromptBuilder promptBuilder
    ) {
        this.chatClient = chatClient;
        this.promptBuilder = promptBuilder;
    }

    @Override
    public List<String> generate(List<MindEntity> minds) {
        String prompt = promptBuilder.build(minds);

        try {
            MindCombinationGenerationResult result = chatClient
                    .prompt()
                    .user(prompt)
                    .call()
                    .entity(
                            MindCombinationGenerationResult.class,
                            spec -> spec
                                    .useProviderStructuredOutput()
                                    .validateSchema()
                    );

            if (result == null || result.suggestions() == null) {
                throw new IllegalStateException(
                        "AI returned an empty response"
                );
            }

            return result.suggestions();

        } catch (Exception exception) {
            throw new AiServiceUnavailableException(
                    "Suggestion generation is temporarily unavailable",
                    exception
            );
        }
    }
}