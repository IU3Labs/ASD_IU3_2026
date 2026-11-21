package ru.bmstu.lab02.benchmark;

/**
 * Единая точка входа для запуска всех бенчмарков лабораторной работы №2.
 */
public final class BenchmarkRunner {

    private BenchmarkRunner() {
    }

    /**
     * Запускает все доступные бенчмарки.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Task1ABenchmark.run();
    }
}
