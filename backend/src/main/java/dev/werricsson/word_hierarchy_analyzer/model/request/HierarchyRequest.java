package dev.werricsson.word_hierarchy_analyzer.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record HierarchyRequest(

        @Size(min=6, message = "must be at least 6 characters")
        @NotBlank(message = "must not be null or empty")
        String category,
        Map<String, Object> subcategories
) {
}
