package ru.bmstu.lab1.task_9;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldAcceptIncreasingArray() {
        assertConsoleOutput(
                "4\n-3 0 2 9\n",
                "YES",
                Main::main);
    }

    @Test
    void shouldAcceptEqualNeighbors() {
        assertConsoleOutput(
                "4\n1 1 2 2\n",
                "YES",
                Main::main);
    }

    @Test
    void shouldRejectDisorderAtBeginning() {
        assertConsoleOutput(
                "4\n2 1 3 4\n",
                "NO",
                Main::main);
    }

    @Test
    void shouldRejectDisorderAtEnd() {
        assertConsoleOutput(
                "4\n1 2 4 3\n",
                "NO",
                Main::main);
    }

    @Test
    void shouldAcceptArrayOfEqualNumbers() {
        assertConsoleOutput(
                "5\n-4 -4 -4 -4 -4\n",
                "YES",
                Main::main);
    }

    @Test
    void shouldRejectDisorderInMiddle() {
        assertConsoleOutput(
                "6\n1 2 5 4 6 7\n",
                "NO",
                Main::main);
    }

    @Test
    void shouldAcceptSortedExtremeIntValues() {
        assertConsoleOutput(
                "3\n-2147483648 0 2147483647\n",
                "YES",
                Main::main);
    }
}