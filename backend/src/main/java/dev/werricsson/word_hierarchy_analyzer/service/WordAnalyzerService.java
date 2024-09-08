package dev.werricsson.word_hierarchy_analyzer.service;

import dev.werricsson.word_hierarchy_analyzer.model.Hierarchy;
import dev.werricsson.word_hierarchy_analyzer.model.response.WordAnalysisResponse;
import dev.werricsson.word_hierarchy_analyzer.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class WordAnalyzerService {

    private final HierarchyService hierarchyService;

    public WordAnalysisResponse analyze(Integer depth, String text) {
        WordAnalysisResponse wordAnalysis = new WordAnalysisResponse();
        Map<String, Integer> categoryCount = wordAnalysis.getHierarchyCount();
        String cleanedText = text.replaceAll("[^\\p{L}\\s]", ""); //Remove caracteres especiais, mantendo os espaços em branco.
        String[] words = cleanedText.split("\\s+"); // Divide o texto, a partir dos espaços em branco.

        int currentDepth = 1;

        // Obtém todas as hierarquias do MongoDB
        List<Hierarchy> hierarchies = hierarchyService.findAll();

        // Itera sobre cada hierarquia
        for (Hierarchy hierarchy : hierarchies) {
            // Acessa as subcategorias
            Map<String, Object> subcategories = hierarchy.getSubcategories();

            if (currentDepth == depth) {
                int count = countWordsInChildrens(subcategories, words);
                categoryCount.put(hierarchy.getCategory(), count);
                wordAnalysis.setDepth(currentDepth);
            } else {
                currentDepth ++;
                analyzeSubcategories(subcategories, words, wordAnalysis, depth, currentDepth);
            }
        }

        if (categoryCount.isEmpty()) {
            throw new ObjectNotFoundException(format("Na frase não existe nenhum filho do nível %d e nem o nível %d possui os termos especificados.", depth, depth));
        }

        return wordAnalysis;
    }

    private void analyzeSubcategories(Map<String, Object> subcategories, String[] words, WordAnalysisResponse wordAnalysis, Integer depth, int currentDepth) {

        for (Map.Entry<String, Object> entry : subcategories.entrySet()) {
            String category = entry.getKey();
            var listOrMap = castEntryValue(entry);
            Map<String, Object> nestedSubcategories = null;

            if (listOrMap instanceof Map) {
                nestedSubcategories = (Map<String, Object>) listOrMap;
            }

            if (currentDepth == depth) {
                if (listOrMap instanceof List) {
                    countWordsInList((List<String>) listOrMap, words, wordAnalysis, category);
                    wordAnalysis.setDepth(currentDepth);
                } else {
                    int count = countWordsInChildrens(nestedSubcategories, words);
                    if (count > 0) {
                        wordAnalysis.getHierarchyCount().put(category, count);
                        wordAnalysis.setDepth(currentDepth);
                    }
                }
            }

            if (nestedSubcategories != null) {
                currentDepth++;
                analyzeSubcategories(nestedSubcategories, words, wordAnalysis, depth, currentDepth);
                currentDepth --;
            }

        }
    }

    private void countWordsInList(List<String> items, String[] words, WordAnalysisResponse wordAnalysis, String category) {

        for (String item : items) {
            for (String word : words) {
                if (word.equalsIgnoreCase(item)) {
                    wordAnalysis.getHierarchyCount().put(
                            category,
                            wordAnalysis.getHierarchyCount()
                                        .getOrDefault(category, 0) + 1
                    );
                }
            }
        }
    }

    private int countWordsInChildrens(Map<String, Object> subcategories, String[] words) {
        int count = 0;

        for (Map.Entry<String, Object> subcategory : subcategories.entrySet()) {
            Object attribute = subcategory.getValue();

            if (attribute instanceof List) {
                List<String> wordList = (List<String>) attribute;
                for (String wordHierarchy : wordList) {
                    for (String wordRequest : words) {
                        //logger.info(wordRequest + " " + wordHierarchy);
                        if(wordRequest.equalsIgnoreCase(wordHierarchy)) {
                            count ++;
                        }
                    }
                }
            } else if (attribute instanceof Map) {
                Map<String, Object> childrenOfSubcategory = (Map<String, Object>) attribute;
                //logger.info("Entrei na recursão");
                count += countWordsInChildrens(childrenOfSubcategory, words);
            }
        }

        return count;
    }

    private <T> T castEntryValue(Map.Entry<String, Object> entry) {
        Object value = entry.getValue();

        if (value instanceof List) {
            List<String> list = (List<String>) value;
            return (T) list;
        }

        if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            return (T) map;
        }

        return null;
    }

}
