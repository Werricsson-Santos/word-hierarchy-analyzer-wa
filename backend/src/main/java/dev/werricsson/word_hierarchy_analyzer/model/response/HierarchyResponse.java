package dev.werricsson.word_hierarchy_analyzer.model.response;

import java.util.Map;

public record HierarchyResponse(
        String id,
        String category,
        Map<String, Object> subcategories
) {
}
