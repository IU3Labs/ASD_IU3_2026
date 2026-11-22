package ru.bmstu.lab02.benchmark;

/**
 * Единая точка входа для запуска всех бенчмарков лабораторной работы №2.
 */
public final class BenchmarkRunner {

    private BenchmarkRunner() {
    }

    /**
     * Запускает все доступные бенчмарки по задачам 1A, 1B, 1C и 1D.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Task1ABenchmark.run();
        Task1BBenchmark.run();
        Task1CBenchmark.run();
        Task1DBenchmark.run();
    }
}
