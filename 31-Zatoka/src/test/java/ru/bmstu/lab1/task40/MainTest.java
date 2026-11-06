package ru.bmstu.lab1.task40;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintArrayWithoutDuplicates() {
        String actual = ConsoleTestHelper.run("6\n1 1 2 2 2 3\n", () -> Main.main(new String[0]));
        assertEquals("1 2 3", actual);
    }
}
