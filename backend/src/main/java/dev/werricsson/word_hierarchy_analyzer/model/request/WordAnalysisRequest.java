package dev.werricsson.word_hierarchy_analyzer.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WordAnalysisRequest {
    Integer depth;
    @NotBlank(message = "Must not be null or empty.")
    String text;
}
