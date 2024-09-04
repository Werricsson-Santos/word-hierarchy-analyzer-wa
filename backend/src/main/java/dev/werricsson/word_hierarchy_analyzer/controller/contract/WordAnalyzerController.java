package dev.werricsson.word_hierarchy_analyzer.controller.contract;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface WordAnalyzerController {
    @PostMapping
    ResponseEntity<Map<String, Integer>> analyze(@RequestBody String text);
}
