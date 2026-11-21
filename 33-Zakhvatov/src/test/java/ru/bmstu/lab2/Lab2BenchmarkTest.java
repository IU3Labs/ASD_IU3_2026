package ru.bmstu.lab2;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** Отдельный воспроизводимый запуск бенчмарков; исключён из обычных юнит-тестов. */
@Tag("benchmark")
class Lab2BenchmarkTest {
    @Test
    void shouldMeasureStructuresAndApplications() throws Exception {
        BenchmarkSupport timer = new BenchmarkSupport();
        String report = environment() + new StructureBenchmark(timer).run() + new ApplicationBenchmark(timer).run();
        report += "\n## Выводы\n\n";
        report += "Время зависит от входных данных, JIT, сборки мусора и внутреннего устройства реализации; ";
        report += "локальные результаты не доказывают превосходство на других нагрузках.\n";
        report += "Массивы собственной таблицы не требуют объектов-узлов для каждой пары, ";
        report += "но расширение и упаковка чисел также выделяют память.\n";
        report += "Показатель памяти — суммарные выделения текущего потока за один прогон, ";
        report += "а не размер удерживаемой кучи или пиковое потребление процесса.\n";
        report += "При небольшом k Top-K хранит только k кандидатов, тогда как полная сортировка копирует весь вход.\n";
        Path output = Path.of(System.getProperty("lab.module"), "benchmarks.md");
        Files.writeString(output, report, StandardCharsets.UTF_8);
        System.out.println("Результаты записаны: " + output);
    }

    private String environment() {
        return "# ЛР2: результаты бенчмарков\n\n"
                + "Дата: " + ZonedDateTime.now() + ".\n\n"
                + "JDK: " + System.getProperty("java.runtime.version") + "; "
                + System.getProperty("java.vendor") + ".\n\n"
                + "ОС: " + System.getProperty("os.name") + " " + System.getProperty("os.version")
                + ", " + System.getProperty("os.arch") + "; процессоров: "
                + Runtime.getRuntime().availableProcessors() + ". JVM: -Xms512m -Xmx1g.\n\n"
                + "Прогрев: " + BenchmarkSupport.WARMUPS + " запусков; измерения: "
                + BenchmarkSupport.REPETITIONS + ". Среднее и σ рассчитаны по отдельным прогонам.\n\n"
                + "Запуск из корня: `mvn -pl 33-Zakhvatov -Pbenchmarks test`. Агент покрытия отключён\n"
                + "только в профиле измерений; обычный `verify` проверяет стиль, тесты и покрытие.\n\n"
                + "Данные создаются до замера, seed фиксирован в исходниках. Внутри замера создаётся\n"
                + "новая структура, выполняются указанные операции и вычисляется контрольный результат.\n"
                + "Используются System.nanoTime и ThreadMXBean.getCurrentThreadAllocatedBytes.\n"
                + "GC и работа ОС могут влиять на разброс; это учебный эксперимент в одной JVM.\n\n";
    }
}
