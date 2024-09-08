package dev.werricsson.word_hierarchy_analyzer.cli;

import picocli.CommandLine;

import java.io.PrintWriter;

public class CLIPrinter {
    private final PrintWriter out;
    private final PrintWriter err;

    public CLIPrinter(CommandLine cmd) {
        this.out = new PrintWriter(cmd.getOut(), true);
        this.err = new PrintWriter(cmd.getErr(), true);
    }

    public void printInfo(String message) {
        out.println(message);
    }

    public void printError(String message) {
        err.println("Erro: " + message);
    }

    public void printTable(String[] headers, String[][] rows) {
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < headers.length; i++) {
            stringBuilder.append(String.format("%-" + columnWidths[i] + "s", headers[i])).append(" | ");
        }
        stringBuilder.append("\n");

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                stringBuilder.append(String.format("%-" + columnWidths[i] + "s", row[i])).append(" | ");
            }
            stringBuilder.append("\n");
        }

        out.println(stringBuilder);
    }
}
