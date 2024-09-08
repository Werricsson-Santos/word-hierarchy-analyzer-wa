package dev.werricsson.word_hierarchy_analyzer.model.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class WordAnalysisResponse {
    Integer depth;
    Map<String, Integer> hierarchyCount;

    public WordAnalysisResponse() {
        this.hierarchyCount = new HashMap<>();
    }

}
