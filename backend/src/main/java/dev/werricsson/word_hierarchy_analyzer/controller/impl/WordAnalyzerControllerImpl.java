package dev.werricsson.word_hierarchy_analyzer.controller.impl;

import dev.werricsson.word_hierarchy_analyzer.controller.contract.WordAnalyzerController;
import dev.werricsson.word_hierarchy_analyzer.model.request.WordAnalysisRequest;
import dev.werricsson.word_hierarchy_analyzer.service.WordAnalyzerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/word-analyzer")
@AllArgsConstructor
public class WordAnalyzerControllerImpl implements WordAnalyzerController {

    private final WordAnalyzerService wordAnalyzerService;


    @Override
    @PostMapping("/analyze")
    public ResponseEntity<Object> analyze(
            @RequestBody WordAnalysisRequest request,
            @RequestParam(required = false) boolean verbose
    ) {
        Integer depth = request.getDepth() != null ? request.getDepth() : 1;


        return ResponseEntity.ok(wordAnalyzerService.analyze(depth, request.getText(), verbose));
    }

}
