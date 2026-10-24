package ru.bmstu.lab1.task_28;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldFindMedianForOddLength() {
        assertConsoleOutput(
                "5\n9 1 5 3 7\n",
                "5.0",
                Main::main);
    }

    @Test
    void shouldFindMedianForEvenLength() {
        assertConsoleOutput(
                "4\n4 1 3 2\n",
                "2.5",
                Main::main);
    }

    @Test
    void shouldHandleNegativeMedian() {
        assertConsoleOutput(
                "4\n-2 -8 -4 -6\n",
                "-5.0",
                Main::main);
    }

    @Test
    void shouldAvoidOverflowWhenAveraging() {
        assertConsoleOutput(
                "2\n2000000000 2000000000\n",
                Double.toString(2000000000.0),
                Main::main);
    }

    @Test
    void shouldPreservePrecisionOfLargeIntegers() {
        assertConsoleOutput(
                "3\n16777216 16777217 16777218\n",
                Double.toString(16777217.0),
                Main::main);
    }

    @Test
    void shouldFindMedianOfEqualNumbers() {
        assertConsoleOutput(
                "4\n7 7 7 7\n",
                "7.0",
                Main::main);
    }

    @Test
    void shouldAverageExtremeIntValues() {
        assertConsoleOutput(
                "2\n-2147483648 2147483647\n",
                "-0.5",
                Main::main);
    }

    @Test
    void shouldPreserveFractionForLargeCentralValues() {
        assertConsoleOutput(
                "2\n16777216 16777217\n",
                Double.toString(16777216.5),
                Main::main);
    }
}