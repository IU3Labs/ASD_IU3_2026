package ru.bmstu.lab1.task25;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintSum() {
        String actual = ConsoleTestHelper.run("4\n3 -1 7 2\n", () -> Main.main(new String[0]));
        assertEquals("11", actual);
    }
}
