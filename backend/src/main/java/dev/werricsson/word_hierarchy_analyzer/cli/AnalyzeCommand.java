package dev.werricsson.word_hierarchy_analyzer.cli;

import dev.werricsson.word_hierarchy_analyzer.model.response.WordAnalysisResponse;
import dev.werricsson.word_hierarchy_analyzer.service.WordAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;


@Command(
        name = "analyze",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Analisa uma frase com base na hierarquia de palavras."
)
class AnalyzeCommand implements Runnable {

    private final WordAnalyzerService wordAnalyzerService;
    @Autowired
    private final CLIPrinter cliPrinter;

    public AnalyzeCommand(WordAnalyzerService wordAnalyzerService, CLIPrinter cliPrinter) {
        this.wordAnalyzerService = wordAnalyzerService;
        this.cliPrinter = cliPrinter;
    }


    @Parameters(index = "0", description = "Chama o método analyze.")
    String analyze;

    @Option(
            names = {"--depth"},
            description = """
                    Profundidade máxima da análise,\s
                    começa a contagem das palavras a partir do parâmetro definido, se não for definido,\s
                    contará a partir do primeiro nível.
                    \s""",
            defaultValue = "1"
    )
    private int depth;

    @Option(names = {"--verbose"}, description = "Imprime a análise completa com a profundidade selecionada e tempo da análise", required = false)
    private boolean verbose;

    @Parameters(index = "1", description = "Texto a ser analisado", arity = "1")
    private String text;

    @Override
    public void run() {
        if (text == null || text.isEmpty()) {
            cliPrinter.printInfo("O parâmetro <text> é obrigatório.");
            System.exit(1);
        }

        var analysisResult = wordAnalyzerService.analyze(depth, text, verbose);

        if(verbose) {
            getVerbosePrint(analysisResult);

        } else {
            getSimplePrint(analysisResult);
        }

    }

    private void getVerbosePrint(Object analysis) {
        WordAnalysisResponse response = (WordAnalysisResponse) analysis;
        Map<String, Integer> hierarchyCount = response.getHierarchyCount();

        for (Map.Entry<String, Integer> match : hierarchyCount.entrySet()) {
            String hirarchyHaveWord = match.getKey();
            Integer countMatch = match.getValue();
            cliPrinter.printInfo(format("%s: %d; ", hirarchyHaveWord, countMatch));
        }

        String[] headers = {"Performance", "Tempo em milisegundos"};
        List<String[]> rowsList = new ArrayList<>();
        for(Map.Entry<String, Long> performAnalyse : response.getPerformAnalysis().entrySet()) {
            String metric = performAnalyse.getKey();
            String measure = format("%d ms", performAnalyse.getValue());

            rowsList.add(new String[]{metric, measure});
        }

        String[][] rows = rowsList.toArray(new String[0][]);

        cliPrinter.printTable(headers, rows);
    }


    private void getSimplePrint(Object analysis) {
        Map<String, Integer> hierarchyCount = (Map<String, Integer>) analysis;

        for (Map.Entry<String, Integer> match : hierarchyCount.entrySet()) {
            String hirarchyHaveWord = match.getKey();
            Integer countMatch = match.getValue();
            cliPrinter.printInfo(format("%s: %d; ", hirarchyHaveWord, countMatch));
        }
    }

}