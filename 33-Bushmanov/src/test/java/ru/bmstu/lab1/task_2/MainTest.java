package ru.bmstu.lab1.task_2;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldReturnFirstMatchingIndex() {
        assertConsoleOutput(
                "4\n4 2 4 1\n4\n",
                "0",
                Main::main);
    }

    @Test
    void shouldReturnMinusOneWhenAbsent() {
        assertConsoleOutput(
                "3\n1 2 3\n8\n",
                "-1",
                Main::main);
    }

    @Test
    void shouldFindLastElement() {
        assertConsoleOutput(
                "4\n8 3 1 -5\n-5\n",
                "3",
                Main::main);
    }

    @Test
    void shouldFindFirstOfSeveralMatchesInsideArray() {
        assertConsoleOutput(
                "6\n8 3 7 3 3 9\n3\n",
                "1",
                Main::main);
    }

    @Test
    void shouldFindZero() {
        assertConsoleOutput(
                "5\n-2 4 0 7 0\n0\n",
                "2",
                Main::main);
    }

    @Test
    void shouldReturnZeroWhenAllElementsMatch() {
        assertConsoleOutput(
                "4\n-5 -5 -5 -5\n-5\n",
                "0",
                Main::main);
    }
}