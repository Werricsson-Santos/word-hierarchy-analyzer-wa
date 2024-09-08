package dev.werricsson.word_hierarchy_analyzer.model.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

import static dev.werricsson.word_hierarchy_analyzer.model.response.AnalysisType.PARAMS;
import static dev.werricsson.word_hierarchy_analyzer.model.response.AnalysisType.PHRASE_CHECK;

@Data
public class WordAnalysisResponse {
    Integer depth;
    Map<String, Integer> hierarchyCount;
    Map<String, Long> performAnalysis;

    public WordAnalysisResponse() {
        this.hierarchyCount = new HashMap<>();
        this.performAnalysis = new HashMap<>();
    }

    public void setHierarchyCount(String hierarchy, Integer countWords) {
        this.hierarchyCount.put(hierarchy, countWords);
    }

    public void setPerformAnalysis(AnalysisType type, Long time) {
        if(type.equals(PARAMS)) {
            this.performAnalysis.put("Tempo de carregamento dos parâmetros", time);
        }

        if (type.equals(PHRASE_CHECK)) {
            this.performAnalysis.put("Tempo de verificação da frase", time);

        }
    }

}
