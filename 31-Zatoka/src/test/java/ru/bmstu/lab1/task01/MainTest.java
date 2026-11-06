package ru.bmstu.lab1.task01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintMinAndMax() {
        String actual = ConsoleTestHelper.run("5\n3 -7 12 0 5\n", () -> Main.main(new String[0]));
        assertEquals("-7 12", actual);
    }
}
