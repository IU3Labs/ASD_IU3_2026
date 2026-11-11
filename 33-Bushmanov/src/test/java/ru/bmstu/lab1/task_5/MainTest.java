package ru.bmstu.lab1.task_5;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldSumElementsAtEvenIndices() {
        assertConsoleOutput(
                "5\n1 100 3 100 5\n",
                "9",
                Main::main);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertConsoleOutput(
                "4\n-5 10 -7 20\n",
                "-12",
                Main::main);
    }

    @Test
    void shouldAvoidIntOverflow() {
        assertConsoleOutput(
                "3\n2000000000 0 2000000000\n",
                "4000000000",
                Main::main);
    }

    @Test
    void shouldUseOnlyFirstElementForLengthTwo() {
        assertConsoleOutput(
                "2\n7 100\n",
                "7",
                Main::main);
    }

    @Test
    void shouldReturnZeroWhenSelectedElementsCancelOut() {
        assertConsoleOutput(
                "5\n10 999 -15 999 5\n",
                "0",
                Main::main);
    }

    @Test
    void shouldAvoidNegativeIntOverflow() {
        assertConsoleOutput(
                "3\n-2000000000 7 -2000000000\n",
                "-4000000000",
                Main::main);
    }
}