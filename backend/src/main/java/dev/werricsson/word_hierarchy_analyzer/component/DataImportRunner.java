package dev.werricsson.word_hierarchy_analyzer.component;

import dev.werricsson.word_hierarchy_analyzer.service.DataImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataImportRunner  implements CommandLineRunner {

    private final DataImportService dataImportService;

    @Override
    public void run(String... args) throws Exception {
        dataImportService.importData();
    }
}
