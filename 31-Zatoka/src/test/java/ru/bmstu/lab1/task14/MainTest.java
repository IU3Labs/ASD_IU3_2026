package ru.bmstu.lab1.task14;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintArraySortedDescending() {
        String actual = ConsoleTestHelper.run("5\n5 -2 9 0 3\n", () -> Main.main(new String[0]));
        assertEquals("9 5 3 0 -2", actual);
    }
}
