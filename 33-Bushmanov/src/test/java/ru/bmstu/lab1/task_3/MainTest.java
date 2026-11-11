package ru.bmstu.lab1.task_3;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldCountRepeatedElement() {
        assertConsoleOutput(
                "4\n4 2 4 4\n4\n",
                "3",
                Main::main);
    }

    @Test
    void shouldReturnZeroWhenAbsent() {
        assertConsoleOutput(
                "3\n1 2 3\n9\n",
                "0",
                Main::main);
    }

    @Test
    void shouldCountAllElements() {
        assertConsoleOutput(
                "4\n-2 -2 -2 -2\n-2\n",
                "4",
                Main::main);
    }

    @Test
    void shouldCountZeros() {
        assertConsoleOutput(
                "6\n0 1 0 -2 0 3\n0\n",
                "3",
                Main::main);
    }

    @Test
    void shouldCountSingleMatchAtEnd() {
        assertConsoleOutput(
                "5\n1 2 3 4 -8\n-8\n",
                "1",
                Main::main);
    }

    @Test
    void shouldCountExtremeIntValue() {
        assertConsoleOutput(
                "4\n-2147483648 0 2147483647 -2147483648\n-2147483648\n",
                "2",
                Main::main);
    }
}