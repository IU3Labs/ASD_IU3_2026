package ru.bmstu.lab1.task32;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintSumOfPairProducts() {
        String actual = ConsoleTestHelper.run("3\n1 2 3\n", () -> Main.main(new String[0]));
        assertEquals("11", actual);
    }
}
