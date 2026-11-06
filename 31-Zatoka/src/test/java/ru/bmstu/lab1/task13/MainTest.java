package ru.bmstu.lab1.task13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintSortedArray() {
        String actual = ConsoleTestHelper.run("5\n5 -2 9 0 3\n", () -> Main.main(new String[0]));
        assertEquals("-2 0 3 5 9", actual);
    }
}
