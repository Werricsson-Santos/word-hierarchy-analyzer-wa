package dev.werricsson.word_hierarchy_analyzer.controller.contract;

import dev.werricsson.word_hierarchy_analyzer.model.request.WordAnalysisRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface WordAnalyzerController {
    @PostMapping
    ResponseEntity<Object> analyze(
            @RequestBody WordAnalysisRequest text,
            @RequestParam(required = false) boolean verbose
    );
}
