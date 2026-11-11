package ru.bmstu.lab1.task_1;

import org.junit.jupiter.api.Test;

import static ru.bmstu.lab1.ConsoleTestSupport.assertConsoleOutput;

class MainTest {

    @Test
    void shouldFindMinAndMaxInMixedArray() {
        assertConsoleOutput(
                "4\n4 -2 7 4\n",
                "-2 7",
                Main::main);
    }

    @Test
    void shouldHandleOnlyNegativeNumbers() {
        assertConsoleOutput(
                "3\n-8 -2 -5\n",
                "-8 -2",
                Main::main);
    }

    @Test
    void shouldHandleEqualNumbers() {
        assertConsoleOutput(
                "3\n5 5 5\n",
                "5 5",
                Main::main);
    }

    @Test
    void shouldHandleExtremeIntValues() {
        assertConsoleOutput(
                "2\n2147483647 -2147483648\n",
                "-2147483648 2147483647",
                Main::main);
    }

    @Test
    void shouldHandleTwoElementsInDescendingOrder() {
        assertConsoleOutput(
                "2\n9 -4\n",
                "-4 9",
                Main::main);
    }

    @Test
    void shouldFindRepeatedMinAndMax() {
        assertConsoleOutput(
                "6\n-7 12 0 -7 12 3\n",
                "-7 12",
                Main::main);
    }
    @Test
    void shouldFindBothExtremesInsideArray() {
        assertConsoleOutput(
                "5\n3 -9 4 15 2\n",
                "-9 15",
                Main::main);
    }
}