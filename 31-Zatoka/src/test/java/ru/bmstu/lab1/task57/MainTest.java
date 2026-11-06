package ru.bmstu.lab1.task57;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleTestHelper;

class MainTest {

    @Test
    void shouldPrintSpiralOrder() {
        String input = "3 4\n1 2 3 4\n5 6 7 8\n9 10 11 12\n";
        String actual = ConsoleTestHelper.run(input, () -> Main.main(new String[0]));
        assertEquals("1 2 3 4 8 12 11 10 9 5 6 7", actual);
    }
}
