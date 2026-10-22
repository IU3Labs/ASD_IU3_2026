package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** Проверяет запуск каждой задачи через main в отдельном процессе Java. */
class MainLaunchTest {

    private static final int PROCESS_TIMEOUT_SECONDS = 15;

    @ParameterizedTest(name = "{0}")
    @MethodSource("consoleExamples")
    void shouldRunMainUsingStandardStreams(String task, String input, String expected) throws Exception {
        Process process = new ProcessBuilder(createCommand(task)).redirectErrorStream(true).start();
        try {
            try (OutputStream keyboard = process.getOutputStream()) {
                keyboard.write(input.getBytes(StandardCharsets.UTF_8));
            }
            assertTrue(process.waitFor(PROCESS_TIMEOUT_SECONDS, TimeUnit.SECONDS), "Программа не завершилась вовремя");
            String actual = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            assertEquals(0, process.exitValue(), actual);
            assertEquals(expected + System.lineSeparator(), actual);
        } finally {
            process.destroyForcibly();
        }
    }

    private static List<String> createCommand(String task) throws Exception {
        String executable = System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java";
        List<String> command = new ArrayList<>();
        command.add(Path.of(System.getProperty("java.home"), "bin", executable).toString());
        for (String argument : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            if (argument.startsWith("-javaagent:") && argument.contains("org.jacoco.agent")) {
                String agent = argument.substring(0, argument.indexOf('='));
                // Отдельный файл покрытия исключает одновременную запись разных процессов.
                command.add(agent + "=destfile=target/jacoco-main-" + task + ".exec,append=false");
            }
        }
        command.add("-cp");
        command.add(Path.of(ru.bmstu.lab1.task_1.Main.class.getProtectionDomain()
                .getCodeSource().getLocation().toURI()).toString());
        command.add("ru.bmstu.lab1." + task + ".Main");
        return command;
    }

    private static Stream<Arguments> consoleExamples() {
        return Stream.of(
                arguments("task_1", "6\n8 -3 12 0 -9 5\n", "-9 12"),
                arguments("task_2", "4\n9 -2 7 -2\n-2\n", "1"),
                arguments("task_3", "5\n4 -2 4 0 4\n4\n", "3"),
                arguments("task_4", "5\n7 2 7 3 7\n7\n", "0 2 4"),
                arguments("task_5", "5\n1 2 3 4 5\n", "9"),
                arguments("task_6", "3\n2 5 8\n", "1"),
                arguments("task_7", "5\n3 9 1 7 5\n", "7"),
                arguments("task_8", "6\n3 -2 0 1 -5 4\n", "3 1 4" + System.lineSeparator() + "-2 -5"),
                arguments("task_13", "4\n4 -2 0 7\n", "-2 0 4 7"),
                arguments("task_49", "2 3\n1 2 3\n4 5 6\n", "6 15"),
                arguments("task_54", "3\n1 2 3\n4 5 6\n7 8 9\n", "15"));
    }
}
