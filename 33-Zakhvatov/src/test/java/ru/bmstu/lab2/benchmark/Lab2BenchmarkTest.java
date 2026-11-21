package ru.bmstu.lab2.benchmark;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** Замеры из условия лабораторной. */
@Tag("benchmark")
class Lab2BenchmarkTest {
    static final int REPEATS = 5;

    @Test
    void shouldMeasureStructuresAndApplications() throws Exception {
        String tables = new StructureBenchmark().run();
        tables += "\n" + new ApplicationBenchmark().run();
        Path output = Path.of(System.getProperty("lab.module"), "benchmarks.md");
        Files.writeString(output, tables, StandardCharsets.UTF_8);
    }

    static String number(double value) {
        return String.format(Locale.ROOT, "%.3f", value);
    }
}
