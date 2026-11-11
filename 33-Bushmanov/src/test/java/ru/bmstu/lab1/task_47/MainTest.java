package ru.bmstu.lab1.task_47;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldRotateSquareMatrixClockwise() {
        assertConsoleOutput(
                "2 2\n" +
                        "1 2\n" +
                        "3 4\n",
                "3 1\n" +
                        "4 2",
                Main::main);
    }

    @Test
    void shouldRotateWideRectangularMatrix() {
        assertConsoleOutput(
                "2 3\n" +
                        "1 2 3\n" +
                        "4 5 6\n",
                "4 1\n" +
                        "5 2\n" +
                        "6 3",
                Main::main);
    }

    @Test
    void shouldRotateTallRectangularMatrix() {
        assertConsoleOutput(
                "3 2\n" +
                        "1 2\n" +
                        "3 4\n" +
                        "5 6\n",
                "5 3 1\n" +
                        "6 4 2",
                Main::main);
    }

    @Test
    void shouldRotateSingleRow() {
        assertConsoleOutput(
                "1 4\n" +
                        "1 2 3 4\n",
                "1\n" +
                        "2\n" +
                        "3\n" +
                        "4",
                Main::main);
    }

    @Test
    void shouldRotateSingleColumn() {
        assertConsoleOutput(
                "4 1\n" +
                        "1\n" +
                        "2\n" +
                        "3\n" +
                        "4\n",
                "4 3 2 1",
                Main::main);
    }

    @Test
    void shouldRotateSingleElementMatrix() {
        assertConsoleOutput(
                "1 1\n" +
                        "7\n",
                "7",
                Main::main);
    }

    @Test
    void shouldRotateMatrixWithNegativeAndExtremeValues() {
        assertConsoleOutput(
                "2 2\n" +
                        "-2147483648 0\n" +
                        "2147483647 -1\n",
                "2147483647 -2147483648\n" +
                        "-1 0",
                Main::main);
    }

    @Test
    void shouldRotateMatrixWithZeros() {
        assertConsoleOutput(
                "2 3\n" +
                        "0 1 0\n" +
                        "-2 0 3\n",
                "-2 0\n" +
                        "0 1\n" +
                        "3 0",
                Main::main);
    }
}