package ru.bmstu.lab1.task_39;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldShiftRightByTwo() {
        assertConsoleOutput(
                "5\n1 2 3 4 5\n2\n",
                "4 5 1 2 3",
                Main::main);
    }

    @Test
    void shouldHandleShiftGreaterThanLength() {
        assertConsoleOutput(
                "5\n1 2 3 4 5\n7\n",
                "4 5 1 2 3",
                Main::main);
    }

    @Test
    void shouldKeepArrayForZeroShift() {
        assertConsoleOutput(
                "4\n1 2 3 4\n0\n",
                "1 2 3 4",
                Main::main);
    }

    @Test
    void shouldKeepArrayForFullRotations() {
        assertConsoleOutput(
                "4\n1 2 3 4\n8\n",
                "1 2 3 4",
                Main::main);
    }

    @Test
    void shouldSwapTwoElementsForShiftOne() {
        assertConsoleOutput(
                "2\n10 -3\n1\n",
                "-3 10",
                Main::main);
    }

    @Test
    void shouldShiftArrayWithZerosAndDuplicates() {
        assertConsoleOutput(
                "5\n0 -2 -2 5 0\n2\n",
                "5 0 0 -2 -2",
                Main::main);
    }

    @Test
    void shouldHandleVeryLargeShift() {
        assertConsoleOutput(
                "5\n1 2 3 4 5\n2147483647\n",
                "4 5 1 2 3",
                Main::main);
    }
}