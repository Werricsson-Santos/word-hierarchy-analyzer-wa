package dev.werricsson.word_hierarchy_analyzer.cli;

import dev.werricsson.word_hierarchy_analyzer.service.WordAnalyzerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

@RequiredArgsConstructor
@Command(
        name = "analyze",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Analisa uma frase com base na hierarquia de palavras."
)
class AnalyzeCommand implements Runnable {

    private final WordAnalyzerService wordAnalyzerService;
    private final Logger logger = LoggerFactory.getLogger(AnalyzeCommand.class);

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
            logger.info("O parâmetro <text> é obrigatório.");
            System.exit(1); // Define um código de saída de erro
        }

        var analysisResult = wordAnalyzerService.analyze(depth, text, verbose);

        logger.info(analysisResult.toString());
    }
}