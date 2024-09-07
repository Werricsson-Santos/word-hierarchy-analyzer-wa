package dev.werricsson.word_hierarchy_analyzer.controller.impl;

import dev.werricsson.word_hierarchy_analyzer.controller.contract.WordAnalyzerController;
import dev.werricsson.word_hierarchy_analyzer.model.request.WordAnalysisRequest;
import dev.werricsson.word_hierarchy_analyzer.service.WordAnalyzerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/word-analyzer")
@AllArgsConstructor
public class WordAnalyzerControllerImpl implements WordAnalyzerController {

    private final WordAnalyzerService wordAnalyzerService;


    @Override
    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Integer>> analyze(@RequestBody WordAnalysisRequest request) {
        // Chama o serviço para analisar o texto
        Map<String, Integer> analysisResult = wordAnalyzerService.analyze(request.getDepth(), request.getText());

        // Retorna a resposta com o resultado da análise
        return ResponseEntity.ok(analysisResult);
    }

}
