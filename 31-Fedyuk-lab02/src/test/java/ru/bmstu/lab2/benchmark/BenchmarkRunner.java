package ru.bmstu.lab2.benchmark;

import java.time.ZonedDateTime;

/** Запускает реальные замеры; стандартные коллекции используются только в тестовом дереве. */
public final class BenchmarkRunner {
    private BenchmarkRunner() {}

    /** Печатает CSV в stdout, сведения о среде — в stderr; аргументы не используются. */
    public static void main(String[] args) {
        System.err.println("Дата запуска: " + ZonedDateTime.now());
        System.err.println("Java: " + System.getProperty("java.runtime.version"));
        System.err.println("VM: " + System.getProperty("java.vm.name"));
        System.err.println(
                "OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        System.err.println("Архитектура: " + System.getProperty("os.arch"));
        System.err.println("Логических процессоров: " + Runtime.getRuntime().availableProcessors());
        System.err.println("Максимальный heap, байт: " + Runtime.getRuntime().maxMemory());
        System.out.println("group,operation,implementation,n,mean_ms,min_ms,max_ms,repetitions");
        HeapBenchmarks.run();
        MapBenchmarks.run();
        WindowBenchmarks.run();
    }
}
