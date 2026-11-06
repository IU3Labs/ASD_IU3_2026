package ru.bmstu.lab1.task19;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintMergedArray() {
        String actual = ConsoleTestHelper.run("3\n1 4 7\n4\n2 3 8 9\n", () -> Main.main(new String[0]));
        assertEquals("1 2 3 4 7 8 9", actual);
    }
}
