package ru.bmstu.lab1.task34;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintMaxSubarraySum() {
        String input = "9\n-2 1 -3 4 -1 2 1 -5 4\n";
        String actual = ConsoleTestHelper.run(input, () -> Main.main(new String[0]));
        assertEquals("6", actual);
    }
}
