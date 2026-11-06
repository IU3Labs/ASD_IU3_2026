package ru.bmstu.lab1.task11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintClosestToAverage() {
        String actual = ConsoleTestHelper.run("3\n1 2 4\n", () -> Main.main(new String[0]));
        assertEquals("2", actual);
    }
}
