package dev.werricsson.word_hierarchy_analyzer.cli;

import de.vandermeer.asciitable.AsciiTable;
import dev.werricsson.word_hierarchy_analyzer.model.response.WordAnalysisResponse;
import dev.werricsson.word_hierarchy_analyzer.service.WordAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.util.Map;

import static de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment.CENTER;
import static java.lang.String.format;


@Command(
        name = "analyze",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Analisa uma frase com base na hierarquia de palavras."
)
class AnalyzeCommand implements Runnable {

    private final WordAnalyzerService wordAnalyzerService;
    private final CLIPrinter cliPrinter;

    @Autowired
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

    @Option(names = {"--verbose"}, description = "Imprime a análise completa com a profundidade selecionada e tempo da análise")
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

        if (verbose) getVerboseAnalysis(analysisResult); else getSimplePrint(analysisResult);

    }

    private void getVerboseAnalysis(Object analysis) {
        AsciiTable table = new AsciiTable();

        WordAnalysisResponse response = (WordAnalysisResponse) analysis;

        Map<String, Integer> hierarchyCount = ((WordAnalysisResponse) analysis).getHierarchyCount();

        hierarchyCount.entrySet()
                .stream()
                .map(match -> format("%s: %d", match.getKey(), match.getValue()))
                .forEach(cliPrinter::printInfo);

        Map<String, Long> performAnalysis = response.getPerformAnalysis();

        //Cria linha fora do loop para o topo da tabela.
            table.addRule();
        for (Map.Entry<String, Long> metric : performAnalysis.entrySet()) {

            table.addRow(metric.getKey(), format("%d ms", metric.getValue()));
            table.addRule();
        }

        //Formata tabela
        table.setPaddingTopChar(' ');
        table.setPaddingBottomChar(' ');
        table.setPaddingLeftChar(' ');
        table.setPaddingRightChar(' ');
        table.setTextAlignment(CENTER);
        table.setPadding(1);

        cliPrinter.printInfo(table.render(80));
    }

    private void getSimplePrint(Object analysis) {
        Map<String, Integer> hierarchyCount = (Map<String, Integer>) analysis;

        hierarchyCount.entrySet()
                .stream()
                .map(match -> format("%s: %d", match.getKey(), match.getValue()))
                .forEach(cliPrinter::printInfo);

    }

}