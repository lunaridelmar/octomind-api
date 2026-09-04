package com.mind.octo.api.mindcombination.dto;

import java.util.List;

public record MindCombinationSuggestionsResponse(
        Long combinationId,
        List<String> suggestions
) {
}