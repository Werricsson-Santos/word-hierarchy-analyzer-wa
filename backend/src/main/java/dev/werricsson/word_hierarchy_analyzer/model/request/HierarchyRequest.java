package dev.werricsson.word_hierarchy_analyzer.model.request;

import java.util.Map;

public record HierarchyRequest(
        String category,
        Map<String, Object> subcategories
) {
}
