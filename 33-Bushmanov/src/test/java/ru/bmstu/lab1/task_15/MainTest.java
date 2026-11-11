package ru.bmstu.lab1.task_15;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldSortReverseArrayAndCountSwaps() {
        assertConsoleOutput(
                "3\n3 2 1\n",
                "1 2 3\n3",
                Main::main);
    }

    @Test
    void shouldMakeNoSwapsForSortedArray() {
        assertConsoleOutput(
                "4\n1 2 3 4\n",
                "1 2 3 4\n0",
                Main::main);
    }

    @Test
    void shouldHandleDuplicates() {
        assertConsoleOutput(
                "3\n2 1 2\n",
                "1 2 2\n1",
                Main::main);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertConsoleOutput(
                "3\n-1 -3 -2\n",
                "-3 -2 -1\n2",
                Main::main);
    }

    @Test
    void shouldMakeNoSwapsForEqualElements() {
        assertConsoleOutput(
                "4\n7 7 7 7\n",
                "7 7 7 7\n0",
                Main::main);
    }

    @Test
    void shouldCountSwapsBetweenDuplicateGroups() {
        assertConsoleOutput(
                "4\n2 2 1 1\n",
                "1 1 2 2\n4",
                Main::main);
    }

    @Test
    void shouldMoveLargestElementFromBeginningToEnd() {
        assertConsoleOutput(
                "5\n5 1 2 3 4\n",
                "1 2 3 4 5\n4",
                Main::main);
    }
}
