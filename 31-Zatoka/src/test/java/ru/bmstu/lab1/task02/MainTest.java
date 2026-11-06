package ru.bmstu.lab1.task02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintFirstIndex() {
        String actual = ConsoleTestHelper.run("5\n7 3 7 3 9\n3\n", () -> Main.main(new String[0]));
        assertEquals("1", actual);
    }

    @Test
    void shouldPrintMinusOneWhenNotFound() {
        String actual = ConsoleTestHelper.run("3\n1 2 3\n10\n", () -> Main.main(new String[0]));
        assertEquals("-1", actual);
    }
}
