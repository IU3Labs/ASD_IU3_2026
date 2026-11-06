package ru.bmstu.lab1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ru.bmstu.lab1.task_1.Task01;
import ru.bmstu.lab1.task_11.Task11;
import ru.bmstu.lab1.task_2.Task02;
import ru.bmstu.lab1.task_3.Task03;
import ru.bmstu.lab1.task_4.Task04;
import ru.bmstu.lab1.task_41.Task41;
import ru.bmstu.lab1.task_5.Task05;
import ru.bmstu.lab1.task_54.Task54;
import ru.bmstu.lab1.task_59.Task59;
import ru.bmstu.lab1.task_6.Task06;

/** Проверяет ввод и вывод через стандартные потоки для всех выбранных задач. */
class TaskConsoleTest {
    @Test
    void shouldReadAndPrintTask01() {
        assertEquals("-2 7", run("4\n1 -2 7 3\n", () -> Task01.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask02() {
        assertEquals("1", run("4\n1 2 3 2\n2\n", () -> Task02.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask03() {
        assertEquals("2", run("3\n5 5 2\n5\n", () -> Task03.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask04() {
        assertEquals("0 2", run("3\n1 2 1\n1\n", () -> Task04.main(new String[0])));
    }

    @Test
    void shouldPrintEmptyLineForMissingIndices() {
        assertEquals("", run("2\n1 2\n5\n", () -> Task04.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask05() {
        assertEquals("4", run("3\n1 9 3\n", () -> Task05.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask06() {
        assertEquals("1", run("4\n1 2 3 6\n", () -> Task06.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask11() {
        assertEquals("3", run("3\n1 3 4\n", () -> Task11.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask41() {
        assertEquals("1 3", run("4\n1 2 2 3\n2\n", () -> Task41.main(new String[0])));
    }

    @Test
    void shouldPrintEmptyLineWhenAllElementsRemoved() {
        assertEquals("", run("2\n3 3\n3\n", () -> Task41.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask54() {
        assertEquals("5", run("2\n1 2\n3 4\n", () -> Task54.main(new String[0])));
    }

    @Test
    void shouldReadAndPrintTask59() {
        assertEquals("1", run("1 0 0\n0 1 0\n0 0 1\n", () -> Task59.main(new String[0])));
    }

    private String run(String source, Runnable action) {
        InputStream previousIn = System.in;
        PrintStream previousOut = System.out;
        ByteArrayOutputStream capture = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(capture, true, StandardCharsets.UTF_8));
            action.run();
        } finally {
            System.setIn(previousIn);
            System.setOut(previousOut);
        }
        return capture.toString(StandardCharsets.UTF_8).trim();
    }
}
