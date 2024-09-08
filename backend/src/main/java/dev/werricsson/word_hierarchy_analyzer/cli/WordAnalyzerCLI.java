package dev.werricsson.word_hierarchy_analyzer.cli;

import dev.werricsson.word_hierarchy_analyzer.service.WordAnalyzerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class WordAnalyzerCLI implements CommandLineRunner {

    private final WordAnalyzerService wordAnalyzerService;
    private final Logger logger = LoggerFactory.getLogger(WordAnalyzerCLI.class);

    @Autowired
    public WordAnalyzerCLI(WordAnalyzerService wordAnalyzerService) {
        this.wordAnalyzerService = wordAnalyzerService;
    }

    @Override
    public void run(String... args)  {
        for (String arg : args) {
            logger.info(arg);
        }
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("analyze")) {
                AnalyzeCommand analyzeCommand = new AnalyzeCommand(wordAnalyzerService);
                CommandLine commandLine = new CommandLine(analyzeCommand);
                int exitCode = commandLine.execute(args);

                System.exit(exitCode);
            } else {
                logger.info("No momento só temos a função analyze, para chamar este método, insira o primeiro paramêtro '<analyze>'");
                System.exit(1);
            }
        }
    }
}