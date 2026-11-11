package ru.bmstu.lab1.task_25;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldSumPositiveNumbers() {
        assertConsoleOutput(
                "5\n1 2 3 4 5\n",
                "15",
                Main::main);
    }

    @Test
    void shouldSumNegativeNumbers() {
        assertConsoleOutput(
                "4\n-1 -2 -3 -4\n",
                "-10",
                Main::main);
    }

    @Test
    void shouldSumMixedNumbers() {
        assertConsoleOutput(
                "5\n3 -2 7 0 4\n",
                "12",
                Main::main);
    }

    @Test
    void shouldReturnZeroForAllZeros() {
        assertConsoleOutput(
                "4\n0 0 0 0\n",
                "0",
                Main::main);
    }

    @Test
    void shouldReturnZeroWhenNumbersCancelOut() {
        assertConsoleOutput(
                "4\n8 -3 -8 3\n",
                "0",
                Main::main);
    }

    @Test
    void shouldHandleTwoElements() {
        assertConsoleOutput(
                "2\n17 -5\n",
                "12",
                Main::main);
    }

    @Test
    void shouldAvoidPositiveIntOverflow() {
        assertConsoleOutput(
                "3\n2000000000 2000000000 2000000000\n",
                "6000000000",
                Main::main);
    }

    @Test
    void shouldAvoidNegativeIntOverflow() {
        assertConsoleOutput(
                "3\n-2000000000 -2000000000 -2000000000\n",
                "-6000000000",
                Main::main);
    }

    @Test
    void shouldSumExtremeIntValues() {
        assertConsoleOutput(
                "2\n-2147483648 2147483647\n",
                "-1",
                Main::main);
    }

    @Test
    void shouldHandleLargestAllowedArraySize() {
        int n = 99999;
        String input = n + "\n" + "1 ".repeat(n) + "\n";

        assertConsoleOutput(
                input,
                "99999",
                Main::main);
    }
}