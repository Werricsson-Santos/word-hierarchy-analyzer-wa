package dev.werricsson.word_hierarchy_analyzer.service;

import dev.werricsson.word_hierarchy_analyzer.model.Hierarchy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WordAnalyzerService {

    private final HierarchyService hierarchyService;

    private final Logger logger = LoggerFactory.getLogger(WordAnalyzerService.class);

    public Map<String, Integer> analyze(String text) {
        Map<String, Integer> categoryCount = new HashMap<>();
        String[] words = text.split("\\s+"); // Divide o texto em palavras

        // Obtém todas as hierarquias do MongoDB
        List<Hierarchy> hierarchies = hierarchyService.findAll();

        // Itera sobre cada hierarquia
        for (Hierarchy hierarchy : hierarchies) {
            // Acessa as subcategorias
            Map<String, Object> subcategories = hierarchy.getSubcategories();
            // Chama o método recursivo para analisar as subcategorias
            analyzeSubcategories(subcategories, words, categoryCount);
        }

        return categoryCount;
    }

    private void analyzeSubcategories(Map<String, Object> subcategories, String[] words, Map<String, Integer> categoryCount) {
        for (Map.Entry<String, Object> entry : subcategories.entrySet()) {
            String category = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof List) {
                // Se for uma lista, conta as palavras
                List<String> animals = (List<String>) value;
                countWordsInList(animals, words, categoryCount, category);
            } else if (value instanceof Map) {
                // Se for um mapa, chama recursivamente
                Map<String, Object> nestedSubcategories = (Map<String, Object>) value;
                logger.info("Going inside category: " + category);
                analyzeSubcategories(nestedSubcategories, words, categoryCount);
            }
        }
    }

    private void countWordsInList(List<String> items, String[] words, Map<String, Integer> categoryCount, String category) {
        for (String item : items) {
            logger.info("Counting item: " + item + " in category: " + category);
            for (String word : words) {
                logger.info("Current word: " + word);
                if (word.equalsIgnoreCase(item)) {
                    logger.info("Counting word: " + word + " for category: " + category);
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            }
        }
    }

}
