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


}
